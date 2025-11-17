package com.example.danceschool.controller;

import com.example.danceschool.dto.QrCodeRequest;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.repository.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class QrCodeController {
    private final LessonRepository lessonRepository;

    public QrCodeController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @PostMapping("/qr")
    public ResponseEntity<?> qr(@RequestBody QrCodeRequest qrCodeRequest) {
        Optional<Lesson> lesson = lessonRepository.findById(qrCodeRequest.getId());
        if (lesson.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body("");
    }
}