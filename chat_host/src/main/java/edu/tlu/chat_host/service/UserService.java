package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.UserRequest;
import edu.tlu.chat_host.dto.UserResponse;
import edu.tlu.chat_host.entity.User;
import edu.tlu.chat_host.mapper.UserMapper;
import edu.tlu.chat_host.repository.ProgramRepository;
import edu.tlu.chat_host.repository.UserRepository;
import edu.tlu.chat_host.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final PasswordEncoder passwordEncoder;
    private final ProgramRepository programRepository;

    public UserResponse update(@NonNull UserRequest request) throws Exception {
        User user = userRepository.getReferenceById(currentUserService.getCurrentUserId());
        if (!(passwordEncoder.encode(request.oldPassword()).equals(user.getPassword()))){
            throw new BadCredentialsException("Old password is incorrect");
        }
        user.setLastName(request.lastName());
        user.setFirstName(request.firstName());
        user.setEmail(request.email());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));}
        user.setPrograms(programRepository.findAllById(request.programIds()));
        return UserMapper.toDto(user);
    }

    public UserResponse getCurrentUserInfo() {
        User user = currentUserService.getCurrentUser();
        return UserMapper.toDto(user);
    }
}
