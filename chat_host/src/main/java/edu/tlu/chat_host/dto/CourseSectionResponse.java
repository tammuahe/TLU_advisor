package edu.tlu.chat_host.dto;

import edu.tlu.chat_host.enums.Semester;

import java.util.List;
import java.util.Set;

public record CourseSectionResponse(Long id, Long subjectId, List<Long> professorIds, Integer year, Semester semester) {
}
