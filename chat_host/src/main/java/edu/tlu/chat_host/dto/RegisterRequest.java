package edu.tlu.chat_host.dto;

public record RegisterRequest(String firstName, String lastName, String studentCode, Long programId, String email,
                              String password) {
}
