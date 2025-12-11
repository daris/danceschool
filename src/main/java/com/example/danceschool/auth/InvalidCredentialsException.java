package com.example.danceschool.auth;

import com.example.danceschool.exception.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}