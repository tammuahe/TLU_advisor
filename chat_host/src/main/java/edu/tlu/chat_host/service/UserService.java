package edu.tlu.chat_host.service;

import edu.tlu.chat_host.entity.User;
import edu.tlu.chat_host.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User save(@NonNull User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(@NonNull Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(@NonNull Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findByProgram(@NonNull Long programId) {
        return userRepository.findByProgramId(programId);
    }

    public boolean isEmailExists(@NonNull String email) {
        return userRepository.existsByEmail(email);
    }

    public User update(@NonNull Long id, @NonNull User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(user.getFirstName());
                    existing.setLastName(user.getLastName());
                    existing.setEmail(user.getEmail());
                    existing.setProgram(user.getProgram());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
