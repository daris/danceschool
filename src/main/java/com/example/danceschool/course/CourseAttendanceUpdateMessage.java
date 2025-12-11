package com.example.danceschool.course;

import com.example.danceschool.attendance.AttendanceStatus;
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
