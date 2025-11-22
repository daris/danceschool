package com.example.danceschool.controller;

import com.example.danceschool.dto.ParticipantDto;
import com.example.danceschool.mapper.ParticipantMapper;
import com.example.danceschool.model.Participant;
import com.example.danceschool.service.ParticipantService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody ParticipantRequest request) {
        ParticipantDto participant = participantService.createParticipant(request.getUserId(), request.getCourseId());
        return ResponseEntity.ok(participant);
    }

    @Data
    public static class ParticipantRequest {
        private UUID userId;
        private UUID courseId;
    }
}