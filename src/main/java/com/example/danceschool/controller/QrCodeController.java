package com.example.danceschool.controller;

import com.example.danceschool.component.SecurityUtils;
import com.example.danceschool.dto.ErrorResponse;
import com.example.danceschool.dto.QrCodeRequest;
import com.example.danceschool.dto.QrCodeResponse;
import com.example.danceschool.model.*;
import com.example.danceschool.repository.LessonRepository;
import com.example.danceschool.repository.ParticipantRepository;
import com.example.danceschool.service.AttendanceService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attendance successfully registered",
                    content = @Content(schema = @Schema(implementation = QrCodeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<?> qr(@RequestBody QrCodeRequest qrCodeRequest) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(qrCodeRequest.getId());
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Lesson not found"));
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

        QrCodeResponse response = new QrCodeResponse();
        response.setMessage("Zarejestrowano wejście na zajęcia: " + course.getName());

        return ResponseEntity.ok().body(response);
    }
}