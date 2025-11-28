package com.example.danceschool.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class LessonRequest {
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private UUID courseId;
}
