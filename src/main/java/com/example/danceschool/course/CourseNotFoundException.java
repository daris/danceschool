package com.example.danceschool.course;

import com.example.danceschool.exception.ResourceNotFoundException;

public class CourseNotFoundException extends ResourceNotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}