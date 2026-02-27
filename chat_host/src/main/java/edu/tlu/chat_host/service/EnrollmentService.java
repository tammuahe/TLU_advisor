package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.EnrollmentResponse;
import edu.tlu.chat_host.entity.Enrollment;
import edu.tlu.chat_host.enums.EnrollmentStatus;
import edu.tlu.chat_host.mapper.EnrollmentMapper;
import edu.tlu.chat_host.repository.EnrollmentRepository;
import edu.tlu.chat_host.security.CurrentUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CurrentUserService currentUserService;

    public EnrollmentResponse findById(@NonNull Long id) {
        return EnrollmentMapper.toDto(enrollmentRepository.findById(id).orElseThrow());
    }

    public List<EnrollmentResponse> getCurrentUserEnrollments() {
        return enrollmentRepository.findByUserId(currentUserService.getCurrentUserId()).stream().map(EnrollmentMapper::toDto).toList();
    }

    public List<EnrollmentResponse> getEnrollmentsBySubject(Long subjectId) {
        return enrollmentRepository.findByUser_IdAndCourseSection_Subject_Id(currentUserService.getCurrentUserId(), subjectId).stream().map(EnrollmentMapper::toDto).toList();
    }

    public List<EnrollmentResponse> getEnrollmentByStatus(EnrollmentStatus status) {
        return enrollmentRepository.findByUserIdAndStatus(currentUserService.getCurrentUserId(), status).stream().map(EnrollmentMapper::toDto).toList();
    }


}
