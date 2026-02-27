package edu.tlu.chat_host.dto;

import java.util.List;

public record UserRequest(String firstName, String lastName, String email, String oldPassword, String password,
                          List<Long> programIds) {
}
