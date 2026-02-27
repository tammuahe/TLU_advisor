package edu.tlu.chat_host.dto;

import edu.tlu.chat_host.entity.Faculty;

public record ProgramResponse(Long id, String name, String level, Faculty faculty) {
}
