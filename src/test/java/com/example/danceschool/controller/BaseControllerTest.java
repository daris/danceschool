package com.example.danceschool.controller;

import com.example.danceschool.jwt.JwtService;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.UserRepository;
import com.example.danceschool.service.CustomUserDetailsService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public abstract class BaseControllerTest {

    protected final String authToken = "dummy-token";

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected CustomUserDetailsService userDetailsService;

    @Autowired
    protected UserRepository userRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean @Primary public JwtService mockJwtService() {
            return Mockito.mock(JwtService.class);
        }
        @Bean @Primary public CustomUserDetailsService mockUserDetailsService() { return Mockito.mock(CustomUserDetailsService.class); }
    }

    void setUpCurrentUser() {
        // Mock JWT behavior
        Mockito.when(jwtService.extractUsername(authToken)).thenReturn("jan");
        Mockito.when(jwtService.isTokenValid(authToken, "jan")).thenReturn(true);

        // Save user to repo
        User user = new User();
        user.setUsername("jan");
        user.setEmail("jan@example.com");
        userRepository.save(user);

        // Mock UserDetailsService
        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername("jan")
                .password("{noop}pass")
                .roles("USER")
                .build();
        Mockito.when(userDetailsService.loadUserByUsername("jan")).thenReturn(userDetails);
    }

    void setUpCurrentUserAdmin() {
        // Mock JWT behavior
        Mockito.when(jwtService.extractUsername(authToken)).thenReturn("jan");
        Mockito.when(jwtService.isTokenValid(authToken, "jan")).thenReturn(true);

        // Save user to repo
        User user = new User();
        user.setUsername("jan");
        user.setEmail("jan@example.com");
        user.setRole("ADMIN");
        userRepository.save(user);

        // Mock UserDetailsService
        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername("jan")
                .password("{noop}pass")
                .roles("ADMIN")
                .build();
        Mockito.when(userDetailsService.loadUserByUsername("jan")).thenReturn(userDetails);
    }
}