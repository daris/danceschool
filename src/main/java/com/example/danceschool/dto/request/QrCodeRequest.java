package com.example.danceschool.dto.request;

import com.example.danceschool.dto.QrCodeType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class QrCodeRequest {
    @NotNull(message = "Lesson id cannot be null")
    private UUID id;
    private QrCodeType type;
}
