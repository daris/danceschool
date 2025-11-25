package com.example.danceschool.controller;

import com.example.danceschool.event.UserEvent;
import com.example.danceschool.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer producer;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        producer.sendMessage("user-created", new UserEvent(UUID.randomUUID(), "sd@dsf.com"));
        return ResponseEntity.ok("Message sent: " + message);
    }
}