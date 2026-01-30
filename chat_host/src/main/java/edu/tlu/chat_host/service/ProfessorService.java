package edu.tlu.chat_host.service;

import edu.tlu.chat_host.entity.Professor;
import edu.tlu.chat_host.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
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

    public Professor save(@NonNull Professor professor) {
        return professorRepository.save(professor);
    }

    public Optional<Professor> findById(@NonNull Long id) {
        return professorRepository.findById(id);
    }

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public void deleteById(@NonNull Long id) {
        professorRepository.deleteById(id);
    }

    public List<Professor> searchByName(@NonNull String name) {
        return professorRepository.findByNameContainingIgnoreCase(name);
    }

    public Professor update(@NonNull Long id, @NonNull Professor professor) {
        return professorRepository.findById(id)
                .map(existing -> {
                    existing.setName(professor.getName());
                    return professorRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
    }
}
