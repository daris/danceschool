package com.example.danceschool.controller;

import com.example.danceschool.component.SecurityUtils;
import com.example.danceschool.dto.*;
import com.example.danceschool.exception.BadRequestException;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.User;
import com.example.danceschool.service.CourseService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class QrCodeController {
    private final SecurityUtils securityUtils;
    private final CourseService courseService;

    @PostMapping("qr")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attendance successfully registered",
                    content = @Content(schema = @Schema(implementation = QrCodeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<?> qr(@Valid @RequestBody QrCodeRequest qrCodeRequest) {
        if (qrCodeRequest.getType() == QrCodeType.LESSON) {
            UUID lessonId = qrCodeRequest.getId();
            User user = securityUtils.getCurrentUser();

            SetAttendanceStatusDto dto = new SetAttendanceStatusDto();
            dto.setLessonId(lessonId);
            dto.setUserId(user.getId());
            dto.setStatus(AttendanceStatus.NORMAL);
            courseService.setAttendanceStatusForLesson(dto);

            CourseDto course = courseService.getCourseForLesson(lessonId);

            QrCodeResponse response = new QrCodeResponse();
            response.setMessage("Registered attendance for a course: " + course.getName());

            return ResponseEntity.ok(response);
        }

        throw new BadRequestException("Invalid type of request");
    }
}