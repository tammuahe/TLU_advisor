package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.*;
import edu.tlu.chat_host.entity.Faculty;
import edu.tlu.chat_host.entity.Professor;
import edu.tlu.chat_host.enums.EnrollmentStatus;
import edu.tlu.chat_host.enums.Semester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.execution.ToolExecutionException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
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
        log.debug("Tool invoked: findCourseSectionById({})", id);
        return courseSectionService.findById(id);
    }

    @Tool(description = "Find all course sections offered by a subject in a semester")
    public List<CourseSectionResponse> findBySubjectAndSemester(@NonNull Long subjectId, @NonNull Semester semester) {
        log.debug("Tool invoked: findBySubjectAndSemester(subjectId={}, semester={})", subjectId, semester);
        return courseSectionService.findBySubjectAndSemester(subjectId, semester);
    }

    @Tool(description = "Find all course sections taught by a professor")
    public List<CourseSectionResponse> findByProfessor(@NonNull Long professorId) {
        log.debug("Tool invoked: findByProfessor({})", professorId);
        return courseSectionService.findByProfessor(professorId);
    }

    @Tool(description = "Find course sections user can enroll in this semester")
    public List<CourseSectionResponse> findEligible() {
        log.debug("Tool invoked: findEligible()");
        return courseSectionService.findEligible();
    }

    @Tool(description = "Get enrollment/grade records")
    public List<EnrollmentResponse> getCurrentUserEnrollments() {
        log.debug("Tool invoked: getCurrentUserEnrollments()");
        return enrollmentService.getCurrentUserEnrollments();
    }

    @Tool(description = "Get enrollment/grade records for a specific subject")
    public List<EnrollmentResponse> getEnrollmentsBySubject(Long subjectId) {
        log.debug("Tool invoked: getEnrollmentsBySubject({})", subjectId);
        return enrollmentService.getEnrollmentsBySubject(subjectId);
    }

    @Tool(description = "Get courses by status")
    public List<EnrollmentResponse> getEnrollmentByStatus(EnrollmentStatus status) {
        log.debug("Tool invoked: getEnrollmentByStatus({})", status);
        return enrollmentService.getEnrollmentByStatus(status);
    }

    @Tool(description = "Search faculty by ID")
    public Faculty findFacultyById(@NonNull Long id) {
        log.debug("Tool invoked: findFacultyById({})", id);
        return facultyService.findById(id);
    }

    @Tool(description = "Find all faculties")
    public List<Faculty> findAllFaculties() {
        log.debug("Tool invoked: findAllFaculties()");
        return facultyService.findAll();
    }

    @Tool(description = "Search faculties by name")
    public List<Faculty> searchFacultiesByName(@NonNull String name) {
        log.debug("Tool invoked: searchFacultiesByName(\"{}\")", name);
        return facultyService.searchByName(name);
    }

    @Tool(description = "Search professor by ID")
    public Professor findProfessorById(@NonNull Long id) {
        log.debug("Tool invoked: findProfessorById({})", id);
        return professorService.findById(id);
    }

    @Tool(description = "Search professors by name")
    public List<Professor> searchProfessorsByName(@NonNull String name) {
        log.debug("Tool invoked: searchProfessorsByName(\"{}\")", name);
        return professorService.searchByName(name);
    }

    @Tool(description = "Get current user's programs")
    public List<ProgramResponse> findUserProgram() {
        log.debug("Tool invoked: findUserProgram()");
        return programService.findUserProgram();
    }

    @Tool(description = "Get current semester")
    public Semester getCurrentSemester() {
        log.debug("Tool invoked: getCurrentSemester()");
        return semesterService.getCurrentSemester();
    }

    @Tool(description = "Get current year")
    public Integer getCurrentYear() {
        log.debug("Tool invoked: getCurrentYear()");
        return semesterService.getCurrentYear();
    }

    @Tool(description = "Find subjects by program ID")
    public List<SubjectResponse> findSubjectByProgramId(@NonNull Long programId) {
        log.debug("Tool invoked: findSubjectByProgramId({})", programId);
        return subjectService.findByProgramId(programId);
    }

    @Tool(description = "Find subject by ID")
    public SubjectResponse findSubjectById(@NonNull Long id) {
        log.debug("Tool invoked: findSubjectById({})", id);
        return subjectService.findById(id);
    }

    @Tool(description = "Get user information")
    public UserResponse getCurrentUserInfo() {
        log.debug("Tool invoked: getCurrentUserInfo()");
        return userService.getCurrentUserInfo();
    }

    @Tool(description = "get student remaining fee that has to be paid to the university of current user. DO NOT ask for student ID or other information")
    public RemainingFeeResponse getRemainingFee() {
        log.debug("Tool invoked: getRemainingFee()");
        try {
            return feeService.getRemainingFee();
        } catch (Exception e) {
            log.debug("Tool getRemainingFee failed: {}", e.getMessage());
            throw new ToolExecutionException(ToolDefinition.builder().inputSchema("{}").name("getRemainingFee")
                    .description("get student remaining fee that has to be paid to the university").build(), e);
        }
    }
}
