package com.example.danceschool.controller;

import com.example.danceschool.dto.AttendanceStatusRequest;
import com.example.danceschool.dto.SetAttendanceStatusDto;
import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.User;
import com.example.danceschool.model.projection.AttendanceExcerpt;
import com.example.danceschool.service.CourseService;
import com.example.danceschool.service.LessonService;
import com.example.danceschool.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendances")
public class AttendanceController {
    private final CourseService courseService;
    private final ProjectionFactory projectionFactory;

    @PostMapping("/set-status")
    public ResponseEntity<AttendanceExcerpt> setStatus(@Valid @RequestBody AttendanceStatusRequest request) {
        SetAttendanceStatusDto dto = new SetAttendanceStatusDto();
        dto.setLessonId(request.getLessonId());
        dto.setUserId(request.getUserId());
        dto.setStatus(request.getStatus());
        dto.setCreateParticipant(true);
        Attendance attendance = courseService.setAttendanceStatusForLesson(dto);

        AttendanceExcerpt attendanceExcerpt = projectionFactory.createProjection(AttendanceExcerpt.class, attendance);
        return ResponseEntity.ok().body(attendanceExcerpt);
    }
}