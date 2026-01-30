package edu.tlu.chat_host.service;

import edu.tlu.chat_host.entity.Faculty;
import edu.tlu.chat_host.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public Faculty save(@NonNull Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> findById(@NonNull Long id) {
        return facultyRepository.findById(id);
    }

    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    public void deleteById(@NonNull Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> searchByName(@NonNull String name) {
        return facultyRepository.findByNameContainingIgnoreCase(name);
    }

    public Faculty update(@NonNull Long id, @NonNull Faculty faculty) {
        return facultyRepository.findById(id)
                .map(existing -> {
                    existing.setName(faculty.getName());
                    return facultyRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + id));
    }
}
