package com.example.danceschool.qr;

import com.example.danceschool.common.exception.BadRequestException;

public class UnknownQrCodeType extends BadRequestException {
    public UnknownQrCodeType(String message) {
        super(message);
    }
}