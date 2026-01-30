package edu.tlu.chat_host.service;

import edu.tlu.chat_host.entity.Enrollment;
import edu.tlu.chat_host.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public Enrollment save(@NonNull Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Optional<Enrollment> findById(@NonNull Long id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public void deleteById(@NonNull Long id) {
        enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> getUserEnrollments(@NonNull Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    public List<Enrollment> getCourseSectionEnrollments(Long courseSectionId) {
        return enrollmentRepository.findByCourseSectionId(courseSectionId);
    }

    public List<Enrollment> getPassedCourses(Long userId) {
        return enrollmentRepository.findByUserIdAndIsPass(userId, true);
    }

    public List<Enrollment> getFailedCourses(Long userId) {
        return enrollmentRepository.findByUserIdAndIsPass(userId, false);
    }

    public Enrollment update(@NonNull Long id, @NonNull Enrollment enrollment) {
        return enrollmentRepository.findById(id)
                .map(existing -> {
                    existing.setUser(enrollment.getUser());
                    existing.setCourseSection(enrollment.getCourseSection());
                    existing.setScore10(enrollment.getScore10());
                    existing.setIsPass(enrollment.getIsPass());
                    existing.setIsCompulsory(enrollment.getIsCompulsory());
                    return enrollmentRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
    }
}
