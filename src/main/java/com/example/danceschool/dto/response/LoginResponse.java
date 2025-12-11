package com.example.danceschool.dto.response;

import com.example.danceschool.user.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private User user;
}