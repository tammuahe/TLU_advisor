package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Long> {

    List<CourseSection> findBySubjectId(Long subjectId);

    List<CourseSection> findBySemester(String semester);

    List<CourseSection> findByProfessorsId(Long professorId);
}
