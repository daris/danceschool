package com.example.danceschool.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ParticipantRequest {
    @NotNull
    private UUID userId;
    @NotNull
    private UUID courseId;
}
