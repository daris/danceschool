package com.example.danceschool.service;

import com.example.danceschool.exception.UserNotFoundException;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}
