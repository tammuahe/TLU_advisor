package edu.tlu.chat_host.mapper;

import edu.tlu.chat_host.dto.PrerequisiteSubjectResponse;
import edu.tlu.chat_host.dto.SubjectResponse;
import edu.tlu.chat_host.entity.Subject;

public class SubjectMapper {

    public static PrerequisiteSubjectResponse toPresrequisite(Subject subject) {
        return new PrerequisiteSubjectResponse(subject.getId(), subject.getCode(), subject.getName());
    }

    public static SubjectResponse toDto(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getCode(),
                subject.getName(),
                subject.getCredits(),
                subject.getFaculty(),
                subject.getPrerequisites().stream().map(SubjectMapper::toPresrequisite).toList(),
                subject.getType()
        );
    }
}
