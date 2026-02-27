package edu.tlu.mcp_document.service;

import java.util.List;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    private final ChatClient chatClient;
    private final PgVectorStore vectorStore;

    //@McpTool(description = "Get document related to user question")
    public List<Document> getDocument(@McpToolParam(description = "User question") String prompt) {
        log.atInfo().setMessage("Prompt: " + prompt).log();
        String hyde = chatClient
                .prompt(new Prompt(prompt, OpenAiChatOptions.builder().temperature(0.0).maxTokens(512).build())).call()
                .content();
        log.atInfo().setMessage("hyDE: " + hyde).log();
        return vectorStore.similaritySearch(
                SearchRequest.builder().query(hyde)
                        .topK(5).build());

    }
}
