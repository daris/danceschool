package com.example.danceschool.exception;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}