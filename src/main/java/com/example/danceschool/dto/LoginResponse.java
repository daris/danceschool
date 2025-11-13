package com.example.danceschool.dto;

import com.example.danceschool.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private User user;
}