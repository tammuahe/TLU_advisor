package edu.tlu.chat_host.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import edu.tlu.chat_host.dto.ChatResponse;
import edu.tlu.chat_host.enums.ChatRole;
import edu.tlu.chat_host.security.CurrentUserService;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final CurrentUserService currentUserService;

    public ChatResponse chat(@NonNull String message) throws BadCredentialsException {
        log.debug("Chat request received, message length={}", message.length());
        String content = chatClient.prompt()
                .advisors(
                // a -> a.param(ChatMemory.CONVERSATION_ID,
                // currentUserService.getCurrentUserId())
                )
                .user(message)
                .call()
                .content();
        log.debug("Chat response received, response length={}", content != null ? content.length() : 0);
        return new ChatResponse(ChatRole.ASSISTANT, content);
    }

}
