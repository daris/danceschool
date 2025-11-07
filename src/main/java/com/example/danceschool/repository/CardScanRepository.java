package com.example.danceschool.repository;

import com.example.danceschool.model.CardScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CardScanRepository extends JpaRepository<CardScan, UUID> {
    List<CardScan> findByUserId(UUID userId);
    List<CardScan> findByCardId(UUID cardId);
    List<CardScan> findByCardIdAndScannedAtAfter(UUID cardId, LocalDateTime date);
}