package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.*;
import edu.tlu.chat_host.entity.Faculty;
import edu.tlu.chat_host.entity.Professor;
import edu.tlu.chat_host.enums.EnrollmentStatus;
import edu.tlu.chat_host.enums.Semester;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.execution.ToolExecutionException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ToolService {

    private final CourseSectionService courseSectionService;
    private final EnrollmentService enrollmentService;
    private final FacultyService facultyService;
    private final ProfessorService professorService;
    private final ProgramService programService;
    private final SemesterService semesterService;
    private final SubjectService subjectService;
    private final UserService userService;
    private final FeeService feeService;

    @Tool(description = "Find course section by ID")
    public CourseSectionResponse findCourseSectionById(@NonNull Long id) {
        return courseSectionService.findById(id);
    }

    @Tool(description = "Find all course sections offered by a subject in a semester")
    public List<CourseSectionResponse> findBySubjectAndSemester(@NonNull Long subjectId, @NonNull Semester semester) {
        return courseSectionService.findBySubjectAndSemester(subjectId, semester);
    }

    @Tool(description = "Find all course sections taught by a professor")
    public List<CourseSectionResponse> findByProfessor(@NonNull Long professorId) {
        return courseSectionService.findByProfessor(professorId);
    }

    @Tool(description = "Find course sections user can enroll in this semester")
    public List<CourseSectionResponse> findEligible() {
        return courseSectionService.findEligible();
    }

    @Tool(description = "Get enrollment/grade records")
    public List<EnrollmentResponse> getCurrentUserEnrollments() {
        return enrollmentService.getCurrentUserEnrollments();
    }

    @Tool(description = "Get enrollment/grade records for a specific subject")
    public List<EnrollmentResponse> getEnrollmentsBySubject(Long subjectId) {
        return enrollmentService.getEnrollmentsBySubject(subjectId);
    }

    @Tool(description = "Get courses by status")
    public List<EnrollmentResponse> getEnrollmentByStatus(EnrollmentStatus status) {
        return enrollmentService.getEnrollmentByStatus(status);
    }

    @Tool(description = "Search faculty by ID")
    public Faculty findFacultyById(@NonNull Long id) {
        return facultyService.findById(id);
    }

    @Tool(description = "Find all faculties")
    public List<Faculty> findAllFaculties() {
        return facultyService.findAll();
    }

    @Tool(description = "Search faculties by name")
    public List<Faculty> searchFacultiesByName(@NonNull String name) {
        return facultyService.searchByName(name);
    }

    @Tool(description = "Search professor by ID")
    public Professor findProfessorById(@NonNull Long id) {
        return professorService.findById(id);
    }

    @Tool(description = "Search professors by name")
    public List<Professor> searchProfessorsByName(@NonNull String name) {
        return professorService.searchByName(name);
    }

    @Tool(description = "Get current user's programs")
    public List<ProgramResponse> findUserProgram() {
        return programService.findUserProgram();
    }

    @Tool(description = "Get current semester")
    public Semester getCurrentSemester() {
        return semesterService.getCurrentSemester();
    }

    @Tool(description = "Get current year")
    public Integer getCurrentYear() {
        return semesterService.getCurrentYear();
    }

    @Tool(description = "Find subjects by program ID")
    public List<SubjectResponse> findSubjectByProgramId(@NonNull Long programId) {
        return subjectService.findByProgramId(programId);
    }

    @Tool(description = "Find subject by ID")
    public SubjectResponse findSubjectById(@NonNull Long id) {
        return subjectService.findById(id);
    }

    @Tool(description = "Get user information")
    public UserResponse getCurrentUserInfo() {
        return userService.getCurrentUserInfo();
    }


    @Tool(description = "get student remaining fee that has to be paid to the university of current user. DO NOT ask for student ID or other information")
    public RemainingFeeResponse getRemainingFee() {
        try {
            return feeService.getRemainingFee();
        } catch (Exception e) {
            throw new ToolExecutionException(ToolDefinition.builder().inputSchema("{}").name("getRemainingFee").description("get student remaining fee that has to be paid to the university").build(), e);
        }
    }
}
