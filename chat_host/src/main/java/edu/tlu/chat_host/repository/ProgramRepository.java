package edu.tlu.chat_host.repository;

import edu.tlu.chat_host.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    List<Program> findByLevel(String level);

    List<Program> findByMajor(String major);
}
