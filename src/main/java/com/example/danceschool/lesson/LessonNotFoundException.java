package com.example.danceschool.lesson;

import com.example.danceschool.exception.ResourceNotFoundException;

public class LessonNotFoundException extends ResourceNotFoundException {
    public LessonNotFoundException(String message) {
        super(message);
    }
}