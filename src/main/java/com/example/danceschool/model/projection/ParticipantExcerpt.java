package com.example.danceschool.model.projection;

import com.example.danceschool.model.Participant;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "participantExcerpt", types = Participant.class)
public interface ParticipantExcerpt {
    UUID getId();

    UserExcerpt getUser();
}
