package edu.tlu.chat_host.mapper;

import edu.tlu.chat_host.dto.EnrollmentResponse;

public class EnrollmentMapper {
    public static EnrollmentResponse toDto(edu.tlu.chat_host.entity.Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getUser().getId(),
                enrollment.getCourseSection().getId(),
                enrollment.getScore10(),
                enrollment.getStatus()
        );
    }
}
