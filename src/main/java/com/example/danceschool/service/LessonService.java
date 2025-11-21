package com.example.danceschool.service;

import com.example.danceschool.exception.ResourceNotFoundException;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;

    public Lesson getById(UUID id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }
}
