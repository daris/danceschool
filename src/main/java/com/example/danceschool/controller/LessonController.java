package com.example.danceschool.controller;

import com.example.danceschool.dto.request.LessonRequest;
import com.example.danceschool.dto.LessonDto;
import com.example.danceschool.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<LessonDto> createLesson(@Valid @RequestBody LessonRequest request) {
        LessonDto lesson = lessonService.createLesson(request.getStartTime(), request.getEndTime(), request.getCourseId());
        return ResponseEntity.ok(lesson);
    }
}