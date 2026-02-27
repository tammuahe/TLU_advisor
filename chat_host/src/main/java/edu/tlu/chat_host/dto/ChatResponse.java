package edu.tlu.chat_host.dto;

import edu.tlu.chat_host.enums.ChatRole;

public record ChatResponse(ChatRole role, String content) {
}
