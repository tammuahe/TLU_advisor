package edu.tlu.chat_host.service;

import edu.tlu.chat_host.security.JwtService;
import edu.tlu.chat_host.dto.AuthRequest;
import edu.tlu.chat_host.dto.AuthResponse;
import edu.tlu.chat_host.dto.RegisterRequest;
import edu.tlu.chat_host.entity.User;
import edu.tlu.chat_host.enums.Role;
import edu.tlu.chat_host.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.firstName()).lastName(request.lastName()).email(request.email()).passwordHash(passwordEncoder.encode(request.password())).roles(Set.of(Role.USER)).build();
        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(request.email());
        return new AuthResponse(jwtService.generateToken(user));
    }
}
