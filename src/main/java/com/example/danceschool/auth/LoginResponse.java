package com.example.danceschool.auth;

import com.example.danceschool.user.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private User user;
}