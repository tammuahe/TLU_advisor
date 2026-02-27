package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.CourseSection;
import edu.tlu.chat_host.entity.Program;
import edu.tlu.chat_host.entity.User;
import edu.tlu.chat_host.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Long> {

    List<CourseSection> findBySubjectId(Long subjectId);

    List<CourseSection> findBySemester(Semester semester);

    List<CourseSection> findByProfessorsId(Long professorId);

    @Query(
            """
                    SELECT cs
                    FROM CourseSection cs
                        JOIN cs.subject s
                    WHERE cs.semester = :semester
                    AND cs.year = :year
                    AND EXISTS (
                        SELECT 1
                        FROM Program p
                        WHERE p IN :programs
                        AND s MEMBER OF p.subjects
                        )
                    AND NOT EXISTS (
                        SELECT 1
                        FROM s.prerequisites prereq
                        WHERE NOT EXISTS (
                        SELECT 1
                        FROM Enrollment e
                        WHERE e.user = :user
                        AND e.status = 'PASSED'
                        AND e.courseSection.subject = prereq))
                    """
    )
    Set<CourseSection> findEligible(User user, Semester semester, Integer year, List<Program> programs);
}
