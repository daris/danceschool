package com.example.danceschool.dto;

import com.example.danceschool.model.AttendanceStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseAttendancesUpdateDto {
    UUID courseId;
    UUID attendanceId;
    UUID lessonId;
    UUID userId;
    AttendanceStatus status;
}
