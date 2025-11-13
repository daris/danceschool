package com.example.danceschool.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class QrCodeRequest {
    private UUID cardId;
    private LocalDateTime currentDateTime;

}
