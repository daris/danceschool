package com.example.danceschool.attendance;

import org.springframework.stereotype.Component;

@Component
public class AttendanceStatusMapper {

    public SetAttendanceStatusDto toDto(AttendanceStatusRequest request) {
        if (request == null) return null;

        SetAttendanceStatusDto dto = new SetAttendanceStatusDto();
        dto.setLessonId(request.getLessonId());
        dto.setUserId(request.getUserId());
        dto.setStatus(request.getStatus() != null ? request.getStatus() : AttendanceStatus.NORMAL);
        dto.setCreateParticipant(true);

        return dto;
    }
}
