package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUserId(Long userId);

    List<Enrollment> findByCourseSectionId(Long courseSectionId);

    List<Enrollment> findByUserIdAndIsPass(Long userId, Boolean isPass);
}
