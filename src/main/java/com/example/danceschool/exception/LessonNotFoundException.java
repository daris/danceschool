package com.example.danceschool.exception;

public class LessonNotFoundException extends ResourceNotFoundException {
    public LessonNotFoundException(String message) {
        super(message);
    }
}