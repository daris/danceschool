package com.example.danceschool.attendance;

import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public AttendanceDto toDto(Attendance attendance) {
        if (attendance == null) return null;

        AttendanceDto dto = new AttendanceDto();
        dto.setId(attendance.getId());
        dto.setLessonId(attendance.getLesson() != null ? attendance.getLesson().getId() : null);
        dto.setUserId(attendance.getUser() != null ? attendance.getUser().getId() : null);
        dto.setStatus(attendance.getStatus());

        return dto;
    }
}