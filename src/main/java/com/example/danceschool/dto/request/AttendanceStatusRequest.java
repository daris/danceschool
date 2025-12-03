package com.example.danceschool.dto.request;

import com.example.danceschool.model.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AttendanceStatusRequest {
    @NotNull
    private UUID lessonId;

    @NotNull
    private UUID userId;

    private AttendanceStatus status;
}
