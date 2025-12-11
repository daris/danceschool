package com.example.danceschool.userpass;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserPassDto {

    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<UUID> courseIds; // map only course IDs
    private LocalDateTime createdAt;
}