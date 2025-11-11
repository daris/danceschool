package com.example.danceschool.model.projection;

import com.example.danceschool.model.Participant;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

@Projection(name = "participantExcerpt", types = Participant.class)
public interface ParticipantExcerpt {
    UUID getId();

    @Value("#{target.user.id}")
    UUID getUserId();
}
