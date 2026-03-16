package edu.tlu.mcp_document.service;

import java.util.List;

import edu.tlu.mcp_document.dto.DocumentChunkDto;
import edu.tlu.mcp_document.mapper.DocumentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Service;

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
                return documentMapper.toDtoList(
                                documentReranker.rerankAndExpandTop(
                                                vectorStore.similaritySearch(
                                                                SearchRequest.builder().query(hyde).topK(7).build()),
                                                5, 2));
        }

        @McpTool(description = "Get information in student handbook related to user question")
        public List<DocumentChunkDto> getHandBook(
                        @McpToolParam(description = "What user is asking for based on convesation history and user intention") String prompt) {
                log.debug("getHandBook prompt: {}", prompt);
                String hyde = chatClient
                                .prompt(new Prompt(prompt,
                                                OpenAiChatOptions.builder().temperature(0.0).maxTokens(512).build()))
                                .call()
                                .content();
                log.debug("getHandBook hyDE: {}", hyde);
                return documentMapper.toDtoList(
                                vectorStore.similaritySearch(
                                                SearchRequest.builder().query(hyde)
                                                                .topK(5)
                                                                .filterExpression("source == 'sổ tay sinh viên'")
                                                                .build()));
        }
}
