package com.example.danceschool.controller;

import com.example.danceschool.dto.*;
import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.User;
import com.example.danceschool.service.AttendanceService;
import com.example.danceschool.service.CourseService;
import com.example.danceschool.service.LessonService;
import com.example.danceschool.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {
    private final LessonService lessonService;
    private final AttendanceService attendanceService;
    private final CourseService courseService;
    private final UserService userService;

    public AttendanceController(LessonService lessonService, AttendanceService attendanceService, CourseService courseService, UserService userService) {
        this.lessonService = lessonService;
        this.attendanceService = attendanceService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping("/set-status")
    public ResponseEntity<?> qr(@Valid @RequestBody AttendanceStatusRequest qrCodeRequest) {
        Lesson lesson = lessonService.getById(qrCodeRequest.getLessonId());
        User user = userService.getById(qrCodeRequest.getUserId());

        Attendance attendance = attendanceService.setAttendanceStatusForLesson(lesson, user, qrCodeRequest.getStatus());
        courseService.notifyCourseAttendancesChanged(lesson, user, attendance);

        return ResponseEntity.ok().build();
    }
}