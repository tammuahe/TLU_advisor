package edu.tlu.chat_host.security;

import edu.tlu.chat_host.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User) {
            return (User) user;
        } else {
            throw new BadCredentialsException("Bad credential");
        }
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
