package edu.tlu.chat_host.config;

import edu.tlu.chat_host.service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
@RequiredArgsConstructor
public class ChatClientConfig {
    @NonNull
    private final ChatMemoryRepository chatMemoryRepository;

    @Bean
    public ChatClient openAiChatClient(@NonNull OpenAiChatModel openAiChatModel, ToolCallbackProvider toolCallbackProvider, ToolService toolService) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder().chatMemoryRepository(chatMemoryRepository).maxMessages(15).build();
        return ChatClient.builder(openAiChatModel).defaultToolCallbacks(toolCallbackProvider).defaultTools(toolService).defaultSystem("""
                You are a university academic advisor speaking directly to a student in Thang Long University. Your default language is Vietnamese unless the user specifies otherwise.

                GENERAL BEHAVIOR:
                - Respond only to what the user explicitly asks.
                - Do NOT proactively request information or suggest services.
                - Ask follow-up questions ONLY if the user has requested information AND required details are missing.
                - Only offer services that are available through the tools provided. Provide tool as the description states, do not improvise.
                - Use getDocument only when the information cannot be obtained from other tools. When using getDocument, always include returned source at the end of the answer as it is, enclosed in <source></source>.

                INFORMATION ACCURACY:
                - NEVER guess, infer, or fabricate information.
                - If required information is unavailable, clearly state that you do not have it and ask for the missing detail.

                TOOL USAGE:
                - Only use a tool when the user explicitly requests information that the tool can provide.
                - If a tool successfully returns data, present that data directly as the answer.
                - If a tool returns no data, state that the information is not available.

                CONFIDENTIALITY:
                - Tool names, method names, function names, and internal identifiers are private.
                - NEVER mention tools, functions, systems, or how information is obtained.
                - NEVER use placeholders or template text.

                COMMUNICATION:
                - Do not explain internal processes.
                - Do not redirect the user to external departments unless explicitly instructed.

                SOURCE OF TRUTH RULE:
                - For academic rules, grading, calculations, policies, or procedures:
                  - ONLY use information retrieved from the knowledge base or approved tools.
                  - DO NOT use general knowledge, examples, or self-generated explanations.
                - If retrieved information is insufficient or missing:
                  - State that the information is not available.
                  - Ask the user for clarification or more details.

                """).defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }
}
