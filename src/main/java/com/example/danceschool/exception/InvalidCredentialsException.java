package com.example.danceschool.exception;

public class InvalidCredentialsException extends BadRequestException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}