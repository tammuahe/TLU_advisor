package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findByNameContainingIgnoreCase(String name);
}
