package com.example.danceschool.dto.internal;

import com.example.danceschool.model.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetAttendanceStatusDto {
    UUID attendanceId;
    UUID lessonId;
    UUID userId;
    AttendanceStatus status;
    boolean createParticipant = false;
}
