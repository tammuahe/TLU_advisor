package edu.tlu.mcp_document.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
@RequiredArgsConstructor
public class ChatClientConfig {

    @Bean
    public ChatClient openAiChatClient(@NonNull OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel).defaultSystem("""
                Generate ONLY a hypothetical university document chunk answering the prompt. The document language is the same as the prompt language.
                """).build();
    }
}
