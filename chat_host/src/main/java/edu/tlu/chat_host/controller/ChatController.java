package edu.tlu.chat_host.controller;

import edu.tlu.chat_host.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public String chat(@RequestParam String conversationId, @RequestParam String prompt) {
        return chatService.chat(conversationId, prompt);
    }

}
