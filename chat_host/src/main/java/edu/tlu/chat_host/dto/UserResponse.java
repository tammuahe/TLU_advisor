package edu.tlu.chat_host.dto;

import java.util.List;

public record UserResponse(String firstName, String lastName, String email, List<Long> programIds) {
}
