package com.example.danceschool.dto.websocket;

import com.example.danceschool.model.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseAttendanceUpdateMessage {
    UUID courseId;
    UUID attendanceId;
    UUID lessonId;
    UUID userId;
    AttendanceStatus status;
}
