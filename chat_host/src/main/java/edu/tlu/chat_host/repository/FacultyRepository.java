package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByNameContainingIgnoreCase(String name);
}
