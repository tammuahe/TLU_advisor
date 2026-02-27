package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.ChatResponse;
import edu.tlu.chat_host.entity.User;
import edu.tlu.chat_host.enums.ChatRole;
import edu.tlu.chat_host.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final CurrentUserService currentUserService;

    public ChatResponse chat(String message) throws BadCredentialsException {
        return new ChatResponse(ChatRole.ASSISTANT, chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, currentUserService.getCurrentUserId()))
                .user(message)
                .call()
                .content());

    }
}
