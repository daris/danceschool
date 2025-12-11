package com.example.danceschool.service;

import com.example.danceschool.auth.UserEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.Mockito.*;

class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendKafkaMessage() {
        // Given
        String topic = "user-created";
        UserEvent event = new UserEvent(UUID.randomUUID(), "test@example.com");

        // When
        kafkaProducer.sendMessage(topic, event);

        // Then
        verify(kafkaTemplate, times(1)).send(topic, event);
    }
}