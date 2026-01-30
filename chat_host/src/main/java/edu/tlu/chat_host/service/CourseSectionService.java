package edu.tlu.chat_host.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tlu.chat_host.entity.CourseSection;
import edu.tlu.chat_host.repository.CourseSectionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseSectionService {

    private final CourseSectionRepository courseSectionRepository;

    public CourseSection save(@NonNull CourseSection courseSection) {
        return courseSectionRepository.save(courseSection);
    }

    public Optional<CourseSection> findById(@NonNull Long id) {
        return courseSectionRepository.findById(id);
    }

    public List<CourseSection> findAll() {
        return courseSectionRepository.findAll();
    }

    public void deleteById(@NonNull Long id) {
        courseSectionRepository.deleteById(id);
    }

    public List<CourseSection> findBySubject(@NonNull Long subjectId) {
        return courseSectionRepository.findBySubjectId(subjectId);
    }


    public List<CourseSection> findBySemester(@NonNull String semester) {
        return courseSectionRepository.findBySemester(semester);
    }

    public List<CourseSection> findByProfessor(@NonNull Long professorId) {
        return courseSectionRepository.findByProfessorsId(professorId);
    }

    public CourseSection update(@NonNull Long id, @NonNull CourseSection courseSection) {
        return courseSectionRepository.findById(id)
                .map(existing -> {
                    existing.setSubject(courseSection.getSubject());
                    existing.setSemester(courseSection.getSemester());
                    existing.setProfessors(courseSection.getProfessors());
                    return courseSectionRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("CourseSection not found with id: " + id));
    }
}
