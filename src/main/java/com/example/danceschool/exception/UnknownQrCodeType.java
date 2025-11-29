package com.example.danceschool.exception;

public class UnknownQrCodeType extends BadRequestException {
    public UnknownQrCodeType(String message) {
        super(message);
    }
}