package com.example.danceschool.component;

import com.example.danceschool.model.User;
import com.example.danceschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new RuntimeException("Unauthorized");
        }

        var userDetails = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}