package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.Enrollment;
import edu.tlu.chat_host.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUserId(Long userId);

    List<Enrollment> findByUser_IdAndCourseSection_Subject_Id(Long UserId, Long subjectId);

    List<Enrollment> findByUserIdAndStatus(Long userId, EnrollmentStatus status);
}
