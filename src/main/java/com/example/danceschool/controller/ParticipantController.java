package com.example.danceschool.controller;

import com.example.danceschool.dto.ParticipantDto;
import com.example.danceschool.dto.ParticipantRequest;
import com.example.danceschool.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@Valid @RequestBody ParticipantRequest request) {
        ParticipantDto participant = participantService.createParticipant(request.getCourseId(), request.getUserId());
        return ResponseEntity.ok(participant);
    }

}