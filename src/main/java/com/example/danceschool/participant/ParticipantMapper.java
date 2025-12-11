package com.example.danceschool.participant;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParticipantMapper {

    public ParticipantDto toDto(Participant participant) {
        if (participant == null) return null;

        ParticipantDto dto = new ParticipantDto();
        dto.setId(participant.getId());
        dto.setUserId(participant.getUser() != null ? participant.getUser().getId() : null);
        dto.setCourseId(participant.getCourse() != null ? participant.getCourse().getId() : null);

        return dto;
    }
}