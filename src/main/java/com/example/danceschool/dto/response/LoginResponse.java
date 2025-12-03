package com.example.danceschool.dto.response;

import com.example.danceschool.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private User user;
}