package edu.tlu.chat_host.dto;

import edu.tlu.chat_host.entity.Faculty;
import edu.tlu.chat_host.enums.SubjectType;

import java.util.List;

public record SubjectResponse(Long id, String code, String name, Integer credits, Faculty faculty,
                              List<PrerequisiteSubjectResponse> prerequisites, SubjectType type) {
}

