package edu.tlu.chat_host.service;

import edu.tlu.chat_host.entity.Professor;
import edu.tlu.chat_host.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public Professor findById(@NonNull Long id) {
        return professorRepository.findById(id).orElseThrow();
    }

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public List<Professor> searchByName(@NonNull String name) {
        return professorRepository.findByNameContainingIgnoreCase(name);
    }

}
