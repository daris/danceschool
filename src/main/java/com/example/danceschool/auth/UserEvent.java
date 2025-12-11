package com.example.danceschool.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserEvent {
    private UUID userId;
    private String email;
}