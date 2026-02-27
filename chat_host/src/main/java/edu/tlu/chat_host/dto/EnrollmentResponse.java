package edu.tlu.chat_host.dto;

import edu.tlu.chat_host.enums.EnrollmentStatus;

public record EnrollmentResponse(Long id, Long userId, Long courseSectionId, Double score10, EnrollmentStatus status) {
}
