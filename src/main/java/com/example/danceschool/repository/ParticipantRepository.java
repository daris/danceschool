package com.example.danceschool.repository;

import com.example.danceschool.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    Optional<Participant> findByCourseIdAndUserId(UUID courseId, UUID userId);
}