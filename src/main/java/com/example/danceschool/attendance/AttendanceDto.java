package com.example.danceschool.attendance;

import lombok.Data;

import java.util.UUID;

@Data
public class AttendanceDto {
    private UUID id;
    private UUID lessonId;
    private UUID userId;
    private AttendanceStatus status;
}