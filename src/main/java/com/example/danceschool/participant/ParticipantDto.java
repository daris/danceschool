package com.example.danceschool.participant;

import lombok.Data;

import java.util.UUID;

@Data
public class ParticipantDto {
    private UUID id;
    private UUID userId;
    private UUID courseId;
}
