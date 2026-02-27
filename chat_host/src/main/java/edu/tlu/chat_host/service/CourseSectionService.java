package edu.tlu.chat_host.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.tlu.chat_host.dto.CourseSectionResponse;
import edu.tlu.chat_host.entity.User;
import edu.tlu.chat_host.enums.Semester;
import edu.tlu.chat_host.mapper.CourseSectionMapper;
import edu.tlu.chat_host.security.CurrentUserService;
import org.springframework.ai.tool.annotation.Tool;
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
    private final CurrentUserService currentUserService;
    private final SemesterService semesterService;

    public CourseSectionResponse save(@NonNull CourseSection courseSection) {
        return CourseSectionMapper.toDto(courseSectionRepository.save(courseSection));
    }

    public CourseSectionResponse findById(@NonNull Long id) {
        return CourseSectionMapper.toDto(courseSectionRepository.findById(id).orElseThrow());
    }

    public List<CourseSectionResponse> findAll() {
        return courseSectionRepository.findAll().stream().map(CourseSectionMapper::toDto).toList();
    }

    public void deleteById(@NonNull Long id) {
        courseSectionRepository.deleteById(id);
    }

    public List<CourseSectionResponse> findBySubjectAndSemester(@NonNull Long subjectId, @NonNull Semester semester) {
        return courseSectionRepository.findBySubjectId(subjectId).stream().map(CourseSectionMapper::toDto).toList();
    }

    public List<CourseSectionResponse> findByProfessor(@NonNull Long professorId) {
        return courseSectionRepository.findByProfessorsId(professorId).stream().map(CourseSectionMapper::toDto).toList();
    }

    public List<CourseSectionResponse> findEligible() {
        User user = currentUserService.getCurrentUser();
        return courseSectionRepository.findEligible(user, semesterService.getCurrentSemester(), semesterService.getCurrentYear(), user.getPrograms()).stream().map(CourseSectionMapper::toDto).toList();
    }
}
