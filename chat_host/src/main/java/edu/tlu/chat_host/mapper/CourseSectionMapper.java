package edu.tlu.chat_host.mapper;

import edu.tlu.chat_host.dto.CourseSectionResponse;
import edu.tlu.chat_host.entity.CourseSection;
import edu.tlu.chat_host.entity.Professor;

public class CourseSectionMapper {
    public static CourseSectionResponse toDto(CourseSection courseSection){
        return new CourseSectionResponse(
                courseSection.getId(),
                courseSection.getSubject().getId(),
                courseSection.getProfessors().stream().map(Professor::getId).toList(),
                courseSection.getYear(),
                courseSection.getSemester()
        );
    }
}
