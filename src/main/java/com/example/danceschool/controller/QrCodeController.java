package com.example.danceschool.controller;

import com.example.danceschool.component.SecurityUtils;
import com.example.danceschool.dto.ErrorResponse;
import com.example.danceschool.dto.QrCodeRequest;
import com.example.danceschool.dto.QrCodeResponse;
import com.example.danceschool.dto.QrCodeType;
import com.example.danceschool.exception.BadRequestException;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.ParticipantRepository;
import com.example.danceschool.service.AttendanceService;
import com.example.danceschool.service.CourseService;
import com.example.danceschool.service.LessonService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrCodeController {
    private final AttendanceService attendanceService;
    private final SecurityUtils securityUtils;
    private final CourseService courseService;
    private final LessonService lessonService;

    public QrCodeController(AttendanceService attendanceService, SecurityUtils securityUtils, ParticipantRepository participantRepository, CourseService courseService, LessonService lessonService) {
        this.attendanceService = attendanceService;
        this.securityUtils = securityUtils;
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @PostMapping("/qr")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attendance successfully registered",
                    content = @Content(schema = @Schema(implementation = QrCodeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<?> qr(@Valid @RequestBody QrCodeRequest qrCodeRequest) {
        if (qrCodeRequest.getType() == QrCodeType.LESSON) {
            Lesson lesson = lessonService.getById(qrCodeRequest.getId());
            User user = securityUtils.getCurrentUser();

            Course course = lesson.getCourse();
            courseService.createParticipantForCourseIfNotAlready(course, user);
            attendanceService.setAttendanceStatusForLesson(lesson, user, AttendanceStatus.NORMAL);

            QrCodeResponse response = new QrCodeResponse();
            response.setMessage("Zarejestrowano wejście na zajęcia: " + course.getName());

            return ResponseEntity.ok().body(response);
        }

        throw new BadRequestException("Invalid type of request");
    }
}