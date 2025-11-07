package com.example.danceschool.service;

import com.example.danceschool.model.CardScan;
import com.example.danceschool.repository.CardScanRepository;
import org.springframework.stereotype.Service;

@Service
public class CardScanService {

    private final CardScanRepository cardScanRepository;

    public CardScanService(CardScanRepository cardScanRepository) {
        this.cardScanRepository = cardScanRepository;
    }

    public CardScan createCardScan(CardScan cardScan) {
        return cardScanRepository.save(cardScan);
    }

}