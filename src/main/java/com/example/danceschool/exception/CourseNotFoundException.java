package com.example.danceschool.exception;

public class CourseNotFoundException extends ResourceNotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}