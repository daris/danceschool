package com.example.danceschool.controller;

import com.example.danceschool.component.SecurityUtils;
import com.example.danceschool.dto.ErrorResponse;
import com.example.danceschool.dto.QrCodeRequest;
import com.example.danceschool.model.*;
import com.example.danceschool.repository.LessonRepository;
import com.example.danceschool.repository.ParticipantRepository;
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
    private final ParticipantRepository participantRepository;

    public QrCodeController(LessonRepository lessonRepository, AttendanceService attendanceService, SecurityUtils securityUtils, ParticipantRepository participantRepository) {
        this.lessonRepository = lessonRepository;
        this.attendanceService = attendanceService;
        this.securityUtils = securityUtils;
        this.participantRepository = participantRepository;
    }

    @PostMapping("/qr")
    public ResponseEntity<?> qr(@RequestBody QrCodeRequest qrCodeRequest) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(qrCodeRequest.getId());
        if (lessonOptional.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Lesson not found"), HttpStatus.NOT_FOUND);
        }

        Lesson lesson = lessonOptional.get();
        User user = securityUtils.getCurrentUser();

        Course course = lesson.getCourse();
        boolean isParticipating = course.getParticipants().stream().anyMatch(participant -> participant.getUser().getId().equals(user.getId()));
        if (!isParticipating) {
            Participant participant = new Participant();
            participant.setUser(user);
            participant.setCourse(course);
            participantRepository.save(participant);
        }

        attendanceService.setAttendanceStatusForLesson(lesson, user, AttendanceStatus.NORMAL);

        return ResponseEntity.ok().body("");
    }
}