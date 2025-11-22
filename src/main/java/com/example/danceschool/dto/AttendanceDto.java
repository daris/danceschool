package com.example.danceschool.dto;

import com.example.danceschool.model.AttendanceStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class AttendanceDto {
    private UUID id;
    private UUID lessonId;
    private UUID userId;
    private AttendanceStatus status;
}