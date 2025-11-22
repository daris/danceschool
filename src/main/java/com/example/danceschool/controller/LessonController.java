package com.example.danceschool.controller;

import com.example.danceschool.dto.LessonDto;
import com.example.danceschool.mapper.LessonMapper;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.service.LessonService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonRequest request) {
        LessonDto lesson = lessonService.createLesson(request.getStartTime(), request.getEndTime(), request.getCourseId());
        return ResponseEntity.ok(lesson);
    }

    @Data
    public static class LessonRequest {
        private Instant startTime;
        private Instant endTime;
        private UUID courseId;
    }
}