package com.example.danceschool.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class QrCodeRequest {
    private UUID cardId;
    private LocalDateTime currentDateTime;

    public QrCodeRequest(UUID cardId) {
        this.cardId = cardId;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }
}
