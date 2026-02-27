package edu.tlu.chat_host.mapper;

import edu.tlu.chat_host.dto.UserResponse;
import edu.tlu.chat_host.entity.Program;
import edu.tlu.chat_host.entity.User;

public class UserMapper {
    public static UserResponse toDto(User user){
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPrograms().stream().map(Program::getId).toList()
        );
    }
}
