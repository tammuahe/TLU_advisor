package edu.tlu.mcp_document.mapper;

import edu.tlu.mcp_document.dto.DocumentChunkDto;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DocumentMapper {

    public DocumentChunkDto toDto(Document doc) {
        Map<String, Object> meta = doc.getMetadata();
        return new DocumentChunkDto(
                doc.getText(),
                meta.getOrDefault("date", "").toString(),
                meta.getOrDefault("source", "").toString());
    }

    public List<DocumentChunkDto> toDtoList(List<Document> documents) {
        return documents.stream().map(this::toDto).toList();
    }
}
