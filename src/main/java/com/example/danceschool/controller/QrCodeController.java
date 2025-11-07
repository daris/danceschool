package com.example.danceschool.controller;

import com.example.danceschool.model.CardScan;
import com.example.danceschool.model.ScheduleEntry;
import com.example.danceschool.model.UserCard;
import com.example.danceschool.repository.CardScanRepository;
import com.example.danceschool.repository.ScheduleEntryRepository;
import com.example.danceschool.repository.UserCardRepository;
import com.example.danceschool.request.QrCodeRequest;
import com.example.danceschool.response.ErrorResponse;
import com.example.danceschool.service.CardScanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class QrCodeController {
    private final CardScanService cardScanService;
    private final UserCardRepository userCardRepository;
    private final CardScanRepository cardScanRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;

    public QrCodeController(CardScanService cardScanService, UserCardRepository userCardRepository, CardScanRepository cardScanRepository, ScheduleEntryRepository scheduleEntryRepository) {
        this.cardScanService = cardScanService;
        this.userCardRepository = userCardRepository;
        this.cardScanRepository = cardScanRepository;
        this.scheduleEntryRepository = scheduleEntryRepository;
    }

    @PostMapping("/qr")
    public ResponseEntity<?> qr(@RequestBody QrCodeRequest qrCodeRequest) {
        Optional<UserCard> userCard = userCardRepository.findById(qrCodeRequest.getCardId());
        if (userCard.isEmpty()) {
            ErrorResponse error = new ErrorResponse(
                    "User card not found with id: " + qrCodeRequest.getCardId(),
                    HttpStatus.NOT_FOUND.value()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        UserCard card = userCard.get();

        List<ScheduleEntry> possibleScheduleEntries = new ArrayList<>();
        LocalDateTime currentDateTime = qrCodeRequest.getCurrentDateTime();

        CardScan currentCardScan = new CardScan();
        currentCardScan.setCard(card);
        currentCardScan.setUser(card.getUser());
        currentCardScan.setScannedAt(currentDateTime);

        // if it's the second scan of that card in current day then guess at which classes that person was
        List<CardScan> todayScans = cardScanRepository.findByCardIdAndScannedAtAfter(card.getId(), currentDateTime.toLocalDate().atStartOfDay());
        if (!todayScans.isEmpty()) {
            CardScan firstCardScan = todayScans.getFirst();
            possibleScheduleEntries = scheduleEntryRepository.findByDayOfWeekAndStartTimeAfterAndEndTimeBefore(currentDateTime.getDayOfWeek(), firstCardScan.getScannedAt().toLocalTime(), currentCardScan.getScannedAt().toLocalTime());
        }

        cardScanService.createCardScan(currentCardScan);

//        return ResponseEntity.accepted().body(String.format("Zarejestrowano skan karty dla użytkownika %s", card.getUser().getUsername()));
        return ResponseEntity.accepted().body(possibleScheduleEntries);
    }
}