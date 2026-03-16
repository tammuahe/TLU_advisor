package edu.tlu.mcp_document.component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DocumentReranker {

    private static final String PRIORITY_SOURCE = "sổ tay sinh viên";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final double SIMILARITY_WEIGHT = 0.5;
    private static final double DATE_WEIGHT = 0.2;
    private static final double SOURCE_WEIGHT = 0.3;

    private static final int EXPAND_WINDOW = 1;

    private static final String FETCH_NEIGHBORS_SQL = """
            SELECT id, content, metadata
            FROM vector_store
            WHERE metadata->>'filename' = ?
              AND (metadata->>'chunk_index')::int BETWEEN ? AND ?
            ORDER BY (metadata->>'chunk_index')::int
            """;

    private final JdbcTemplate jdbc;
    private final ObjectMapper objectMapper;

    public DocumentReranker(JdbcTemplate jdbc, ObjectMapper objectMapper) {
        this.jdbc = jdbc;
        this.objectMapper = objectMapper;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public List<Document> rerank(List<Document> documents) {
        return rerank(documents, false);
    }

    public List<Document> rerankAndExpand(List<Document> documents) {
        return rerank(documents, true);
    }

    /**
     * Reranks all documents, limits to {@code totalLimit}, then expands only the
     * top
     * {@code expandTop} chunks. The remaining slots are returned without expansion.
     *
     * @param documents  candidates from vector search
     * @param totalLimit total number of chunks to return
     * @param expandTop  how many top-ranked chunks to expand (must be <=
     *                   totalLimit)
     */
    public List<Document> rerankAndExpandTop(List<Document> documents, int totalLimit, int expandTop) {
        if (documents == null || documents.isEmpty())
            return documents;

        List<Document> reranked = rerank(documents, false);

        List<Document> top = reranked.stream()
                .limit(totalLimit)
                .collect(Collectors.toList());

        List<Document> result = new ArrayList<>(top.size());
        for (int i = 0; i < top.size(); i++) {
            result.add(i < expandTop ? expandChunk(top.get(i)) : top.get(i));
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Rerank
    // -------------------------------------------------------------------------

    private List<Document> rerank(List<Document> documents, boolean expand) {
        if (documents == null || documents.isEmpty())
            return documents;

        double maxSimilarity = documents.stream()
                .map(Document::getScore)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .max().orElse(1.0);

        List<Long> epochs = extractAllEpochs(documents);
        long minEpoch = epochs.stream().mapToLong(Long::longValue).min().orElse(0L);
        long maxEpoch = epochs.stream().mapToLong(Long::longValue).max().orElse(1L);
        long epochRange = Math.max(maxEpoch - minEpoch, 1L);

        List<Document> reranked = documents.stream()
                .map(doc -> {
                    double composite = computeCompositeScore(doc, maxSimilarity, minEpoch, epochRange);
                    return doc.mutate().score(composite).build();
                })
                .sorted(Comparator.comparingDouble((Document doc) -> doc.getScore() != null ? doc.getScore() : 0.0)
                        .reversed())
                .collect(Collectors.toList());

        if (!expand)
            return reranked;

        return reranked.stream()
                .map(this::expandChunk)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    // Scoring
    // -------------------------------------------------------------------------

    private double computeCompositeScore(Document doc, double maxSimilarity,
            long minEpoch, long epochRange) {
        double similarityScore = (doc.getScore() != null && maxSimilarity > 0)
                ? doc.getScore() / maxSimilarity
                : 0.0;

        double dateScore = computeDateScore(doc, minEpoch, epochRange);
        double sourceScore = isPrioritySource(doc) ? 1.0 : 0.0;

        return (SIMILARITY_WEIGHT * similarityScore)
                + (DATE_WEIGHT * dateScore)
                + (SOURCE_WEIGHT * sourceScore);
    }

    private double computeDateScore(Document doc, long minEpoch, long epochRange) {
        long epoch = parseEpoch(doc);
        if (epoch == -1L)
            return 0.0;
        return (double) (epoch - minEpoch) / epochRange;
    }

    private boolean isPrioritySource(Document doc) {
        Object source = doc.getMetadata().get("source");
        return source instanceof String s
                && s.trim().toLowerCase().contains(PRIORITY_SOURCE.toLowerCase());
    }

    // -------------------------------------------------------------------------
    // Chunk expansion
    // -------------------------------------------------------------------------

    private Document expandChunk(Document doc) {
        Map<String, Object> meta = doc.getMetadata();

        Object filenameObj = meta.get("filename");
        Object chunkIndexObj = meta.get("chunk_index");
        Object totalChunksObj = meta.get("total_chunks");

        if (filenameObj == null || chunkIndexObj == null) {
            log.warn("Missing filename or chunk_index in metadata: {}", meta);
            return doc;
        }

        String filename = filenameObj.toString();
        int chunkIndex = ((Number) chunkIndexObj).intValue();
        int totalChunks = totalChunksObj != null
                ? Integer.parseInt(totalChunksObj.toString())
                : Integer.MAX_VALUE;

        int fromIndex = Math.max(0, chunkIndex - EXPAND_WINDOW);
        int toIndex = Math.min(totalChunks - 1, chunkIndex + EXPAND_WINDOW);

        log.debug("Expanding chunk: filename={}, chunkIndex={}, range=[{},{}]",
                filename, chunkIndex, fromIndex, toIndex);

        try {
            List<Document> neighbors = jdbc.query(
                    FETCH_NEIGHBORS_SQL, this::mapRow, filename, fromIndex, toIndex);

            log.debug("Fetched {} neighbors for chunk {}", neighbors.size(), chunkIndex);
            neighbors.forEach(n -> log.debug("  neighbor chunk_index={}, text_length={}",
                    n.getMetadata().get("chunk_index"),
                    n.getText() != null ? n.getText().length() : 0));

            String expandedText = neighbors.stream()
                    .sorted(Comparator.comparingInt(d -> ((Number) d.getMetadata().get("chunk_index")).intValue()))
                    .map(Document::getText)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("\n"));

            log.debug("Original text length={}, expanded text length={}",
                    doc.getText() != null ? doc.getText().length() : 0,
                    expandedText.length());

            return doc.mutate().text(expandedText).build();

        } catch (Exception e) {
            log.warn("Failed to expand chunk {} of '{}': {}", chunkIndex, filename, e.getMessage(), e);
            return doc;
        }
    }

    private Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            String id = rs.getString("id");
            String content = rs.getString("content");
            Map<String, Object> metadata = objectMapper.readValue(
                    rs.getString("metadata"), new TypeReference<>() {
                    });

            return Document.builder()
                    .id(id)
                    .text(content)
                    .metadata(metadata)
                    .build();
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to parse metadata JSON for row " + rowNum, e);
        }
    }

    // -------------------------------------------------------------------------
    // Date helpers
    // -------------------------------------------------------------------------

    private long parseEpoch(Document doc) {
        Object val = doc.getMetadata().get("date");
        if (val == null)
            return -1L;

        String dateStr = val.toString().trim();
        try {
            return LocalDateTime.parse(dateStr, DATE_FORMATTER).toEpochSecond(ZoneOffset.UTC);
        } catch (DateTimeParseException e) {
            try {
                return Instant.parse(dateStr).getEpochSecond();
            } catch (Exception ignored) {
            }
        }
        return -1L;
    }

    private List<Long> extractAllEpochs(List<Document> docs) {
        return docs.stream()
                .map(this::parseEpoch)
                .filter(e -> e != -1L)
                .collect(Collectors.toList());
    }
}