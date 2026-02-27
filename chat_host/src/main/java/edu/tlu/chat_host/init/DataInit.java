package edu.tlu.chat_host.init;

import edu.tlu.chat_host.entity.User;
import edu.tlu.chat_host.enums.Role;
import edu.tlu.chat_host.repository.ProgramRepository;
import edu.tlu.chat_host.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProgramRepository programRepository;

    @PostConstruct
    public void addUser() {
        userRepository.save(
                User.builder().email("admin").passwordHash(passwordEncoder.encode("admin")).roles(Set.of(Role.ADMIN)).build()
        );
        userRepository.save(
                User.builder().code("A44249").firstName("Tâm").lastName("Nguyễn Đức").email("tammuahe2004@gmail.com").passwordHash(passwordEncoder.encode("test")).programs(programRepository.findByNameContainingIgnoreCase("công nghệ thông tin")).roles(Set.of(Role.USER)).build()
        );
    }
}
