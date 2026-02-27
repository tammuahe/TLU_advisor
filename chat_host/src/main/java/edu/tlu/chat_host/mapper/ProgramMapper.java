package edu.tlu.chat_host.mapper;

import edu.tlu.chat_host.dto.ProgramResponse;
import edu.tlu.chat_host.entity.Program;

public class ProgramMapper {

    public static ProgramResponse toDto(Program program) {
        return new ProgramResponse(
                program.getId(),
                program.getName(),
                program.getLevel().name(),
                program.getFaculty()
        );
    }
}
