package com.example.danceschool.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class LessonDto {
    private UUID id;
    private Instant startTime;
    private Instant endTime;
    private List<AttendanceDto> attendances;
}