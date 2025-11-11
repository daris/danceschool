package com.example.danceschool.repository;

import com.example.danceschool.model.Participant;
import com.example.danceschool.model.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participant, UUID> {
}