package edu.tlu.mcp_document.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tlu.mcp_document.dto.DocumentChunkDto;
import edu.tlu.mcp_document.mapper.DocumentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tlu.mcp_document.component.DocumentReranker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

        private final ChatClient chatClient;
        private final PgVectorStore vectorStore;
        private final DocumentReranker documentReranker;
        private final DocumentMapper documentMapper;

        private void saveToFile(String json) {
                try (FileWriter fw = new FileWriter("ragas_eval.jsonl", true)) {
                        fw.write(json + "\n");
                } catch (IOException e) {
                        log.error("File write failed", e);
                }
        }

        /**
         * Logs a RAGAS-compatible JSONL entry for retrieval evaluation.
         * Matches the SingleTurnSample schema from the ragas library:
         *   - user_input: the original user question passed to the tool
         *   - retrieved_contexts: flat list of retrieved chunk texts
         *   - hyde_query: the HyDE-expanded query used for vector search (debug info)
         *   - context_sources: source metadata per chunk (debug info)
         * The `response` and `reference` fields are written by the chat_host service
         * in its own ragas_eval.jsonl, keyed by the same user_input.
         */
        private void logRetrievedDocuments(String query, String hyde, List<Document> docs) {
                try {
                        // RAGAS expects contexts as a flat list of strings
                        List<String> contexts = docs.stream()
                                        .map(Document::getText)
                                        .toList();

                        // Source name of each retrieved chunk for debugging
                        List<String> contextSources = docs.stream()
                                        .map(doc -> (String) doc.getMetadata().getOrDefault("source", "unknown"))
                                        .toList();

                        Map<String, Object> logData = new HashMap<>();
                        logData.put("user_input", query);              // RAGAS SingleTurnSample field
                        logData.put("retrieved_contexts", contexts);    // RAGAS SingleTurnSample field
                        logData.put("hyde_query", hyde);                // debug: HyDE-expanded query
                        logData.put("context_sources", contextSources); // debug: source per chunk

                        String json = new ObjectMapper().writeValueAsString(logData);

                        log.info("RAGAS_EVAL {}", json);
                        saveToFile(json);

                } catch (Exception e) {
                        log.error("Failed to log retrieved documents", e);
                }
        }

        @McpTool(description = "Get document related to user question")
        public List<DocumentChunkDto> getDocument(
                        @McpToolParam(description = "What user is asking for based on convesation history and user intention") String prompt) {
                log.atInfo().setMessage("Prompt: " + prompt).log();
                String hyde = chatClient
                                .prompt(new Prompt(prompt,
                                                OpenAiChatOptions.builder().temperature(0.0).maxTokens(512).build()))
                                .call()
                                .content();
                log.atInfo().setMessage("hyDE: " + hyde).log();
                // Fetch 7 candidates, rerank all, expand only the top 2, return 5 total
                List<Document> retrieved = documentReranker.rerankAndExpandTop(
                                vectorStore.similaritySearch(
                                                SearchRequest.builder().query(hyde).topK(7).build()),
                                5, 2);
                logRetrievedDocuments(prompt, hyde, retrieved);
                return documentMapper.toDtoList(retrieved);
        }

        // @McpTool(description = "Get information in student handbook related to user question")
        // public List<DocumentChunkDto> getHandBook(
        //                 @McpToolParam(description = "What user is asking for based on convesation history and user intention") String prompt) {
        //         log.debug("getHandBook prompt: {}", prompt);
        //         String hyde = chatClient
        //                         .prompt(new Prompt(prompt,
        //                                         OpenAiChatOptions.builder().temperature(0.0).maxTokens(512).build()))
        //                         .call()
        //                         .content();
        //         log.debug("getHandBook hyDE: {}", hyde);
        //         return documentMapper.toDtoList(
        //                         vectorStore.similaritySearch(
        //                                         SearchRequest.builder().query(hyde)
        //                                                         .topK(5)
        //                                                         .filterExpression("source == 'sổ tay sinh viên'")
        //                                                         .build()));
        // }
}
