package edu.tlu.chat_host.service;

import edu.tlu.chat_host.entity.Program;
import edu.tlu.chat_host.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;

    public Program save(@NonNull Program program) {
        return programRepository.save(program);
    }

    public Optional<Program> findById(@NonNull Long id) {
        return programRepository.findById(id);
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public void deleteById(@NonNull Long id) {
        programRepository.deleteById(id);
    }

    public List<Program> findByLevel(@NonNull String level) {
        return programRepository.findByLevel(level);
    }

    public List<Program> findByMajor(@NonNull String major) {
        return programRepository.findByMajor(major);
    }

    public Program update(@NonNull Long id, @NonNull Program program) {
        return programRepository.findById(id)
                .map(existing -> {
                    existing.setName(program.getName());
                    existing.setLevel(program.getLevel());
                    existing.setMajor(program.getMajor());
                    existing.setTrainingMode(program.getTrainingMode());
                    return programRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Program not found with id: " + id));
    }
}
