package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.Subject;
import edu.tlu.chat_host.enums.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByFacultyId(Long facultyId);

    List<Subject> findByCredits(Integer credits);
}
