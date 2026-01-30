package edu.tlu.chat_host.service;

import edu.tlu.chat_host.entity.Subject;
import edu.tlu.chat_host.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject save(@NonNull Subject subject) {
        return subjectRepository.save(subject);
    }

    public Optional<Subject> findById(@NonNull Long id) {
        return subjectRepository.findById(id);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public void deleteById(@NonNull Long id) {
        subjectRepository.deleteById(id);
    }

    public List<Subject> findByFaculty(@NonNull Long facultyId) {
        return subjectRepository.findByFacultyId(facultyId);
    }

    public List<Subject> findByCredits(Integer credits) {
        return subjectRepository.findByCredits(credits);
    }

    public Subject update(@NonNull Long id, @NonNull Subject subject) {
        return subjectRepository.findById(id)
                .map(existing -> {
                    existing.setName(subject.getName());
                    existing.setCredits(subject.getCredits());
                    existing.setFaculty(subject.getFaculty());
                    existing.setPrerequisites(subject.getPrerequisites());
                    return subjectRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }
}
