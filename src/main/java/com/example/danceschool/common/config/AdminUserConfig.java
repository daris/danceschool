package com.example.danceschool.common.config;

import com.example.danceschool.user.User;
import com.example.danceschool.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class AdminUserConfig {

    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username:}")
    private String adminUsername;

    @Value("${admin.password:admin123}")
    private String adminPassword;

    @Value("${admin.email:admin@example.com}")
    private String adminEmail;

    @Bean
    CommandLineRunner createAdminUser(UserRepository userRepository) {
        return args -> {
            // Only proceed if username is defined
            if (adminUsername != null && !adminUsername.isEmpty()) {
                if (userRepository.findByUsername(adminUsername).isEmpty()) {
                    User admin = new User();
                    admin.setUsername(adminUsername);
                    admin.setPassword(passwordEncoder.encode(adminPassword));
                    admin.setEmail(adminEmail);
                    admin.setFirstName("Admin");
                    admin.setLastName("User");
                    admin.setRole("ADMIN");
                    admin.setCreatedAt(LocalDateTime.now());

                    userRepository.save(admin);
                    log.info("Admin user created: {}", adminUsername);
                } else {
                    log.info("Admin user already exists: {}", adminUsername);
                }
            } else {
                log.info("No admin.username defined. Skipping admin creation.");
            }
        };
    }
}