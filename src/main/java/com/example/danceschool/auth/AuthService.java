package com.example.danceschool.auth;

import com.example.danceschool.event.UserEvent;
import com.example.danceschool.exception.InvalidCredentialsException;
import com.example.danceschool.jwt.JwtService;
import com.example.danceschool.service.CustomUserDetailsService;
import com.example.danceschool.service.KafkaProducer;
import com.example.danceschool.user.User;
import com.example.danceschool.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final KafkaProducer producer;

    public void registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("USER");

        userRepository.save(user);

        producer.sendMessage("user-created", new UserEvent(user.getId(), user.getUsername()));
    }

    public LoginResponse loginUser(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = (User) userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtService.generateToken(user.getUsername());

            LoginResponse response = new LoginResponse();
            response.setAccessToken(token);
            response.setUser(user);

            return response;

        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}