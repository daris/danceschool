package com.example.danceschool.participant;

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