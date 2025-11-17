package com.example.danceschool.controller;

import com.example.danceschool.component.SecurityUtils;
import com.example.danceschool.dto.ErrorResponse;
import com.example.danceschool.dto.QrCodeRequest;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.repository.LessonRepository;
import com.example.danceschool.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class QrCodeController {
    private final LessonRepository lessonRepository;
    private final AttendanceService attendanceService;
    private final SecurityUtils securityUtils;

    public QrCodeController(LessonRepository lessonRepository, AttendanceService attendanceService, SecurityUtils securityUtils) {
        this.lessonRepository = lessonRepository;
        this.attendanceService = attendanceService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("/qr")
    public ResponseEntity<?> qr(@RequestBody QrCodeRequest qrCodeRequest) {
        Optional<Lesson> lesson = lessonRepository.findById(qrCodeRequest.getId());
        if (lesson.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Lesson not found"), HttpStatus.NOT_FOUND);
        }

        attendanceService.setAttendanceStatusForLesson(lesson.get(), securityUtils.getCurrentUser(), AttendanceStatus.NORMAL);

        return ResponseEntity.ok().body("");
    }
}