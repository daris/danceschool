package com.example.danceschool.controller;

import com.example.danceschool.dto.AttendanceDto;
import com.example.danceschool.dto.request.AttendanceStatusRequest;
import com.example.danceschool.dto.SetAttendanceStatusDto;
import com.example.danceschool.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/set-status")
    public ResponseEntity<AttendanceDto> setStatus(@Valid @RequestBody AttendanceStatusRequest request) {
        SetAttendanceStatusDto dto = new SetAttendanceStatusDto();
        dto.setLessonId(request.getLessonId());
        dto.setUserId(request.getUserId());
        dto.setStatus(request.getStatus());
        dto.setCreateParticipant(true);

        AttendanceDto attendance = courseService.setAttendanceStatusForLesson(dto);
        return ResponseEntity.ok(attendance);
    }
}