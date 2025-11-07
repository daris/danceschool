package com.example.danceschool.controller;

import com.example.danceschool.model.ScheduleEntry;
import com.example.danceschool.model.User;
import com.example.danceschool.model.UserCard;
import com.example.danceschool.repository.ScheduleEntryRepository;
import com.example.danceschool.repository.UserRepository;
import com.example.danceschool.request.QrCodeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // rollback after each test
class QrCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleEntryRepository scheduleEntryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUser_shouldReturnUserFromDatabase() throws Exception {
        // given
        List<User> users = new ArrayList<>();
        User user;

        user = new User();
        user.setId(UUID.fromString("57dfee15-304a-4c88-923d-0d7855b889f6"));
        user.setUsername("jan");
        user.setEmail("jan@example.com");

        List<UserCard> userCards = new ArrayList<>();
        UserCard userCard =  new UserCard();
        userCard.setId(UUID.fromString("0bf9f152-440f-462d-a31f-c70b974759af"));
        userCard.setActive(true);
        userCard.setUser(user);
        userCards.add(userCard);
        user.setCards(userCards);

        users.add(user);

        userRepository.saveAll(users);

        List<ScheduleEntry> scheduleEntries = new ArrayList<>();
        ScheduleEntry scheduleEntry;

        scheduleEntry = new ScheduleEntry();
        scheduleEntry.setId(UUID.fromString("065fe773-9c49-48e5-8fff-c830443176dd"));
        scheduleEntry.setName("Salsa cubana");
        scheduleEntry.setLevel("P1");
        scheduleEntry.setDayOfWeek(DayOfWeek.MONDAY);
        scheduleEntry.setStartTime(LocalTime.parse("17:00"));
        scheduleEntry.setEndTime(LocalTime.parse("18:00"));
        scheduleEntry.setStartDate(LocalDate.parse("2025-01-01"));
        scheduleEntries.add(scheduleEntry);

        scheduleEntry = new ScheduleEntry();
        scheduleEntry.setId(UUID.fromString("ce3fbf84-da51-4b1e-b8d9-84e57178bbcf"));
        scheduleEntry.setName("Salsa LA");
        scheduleEntry.setLevel("P2");
        scheduleEntry.setDayOfWeek(DayOfWeek.MONDAY);
        scheduleEntry.setStartTime(LocalTime.parse("18:00"));
        scheduleEntry.setEndTime(LocalTime.parse("19:00"));
        scheduleEntry.setStartDate(LocalDate.parse("2025-01-01"));
        scheduleEntries.add(scheduleEntry);

        scheduleEntryRepository.saveAll(scheduleEntries);

        QrCodeRequest qrCodeRequest = new QrCodeRequest();
        qrCodeRequest.setCardId(UUID.fromString("0bf9f152-440f-462d-a31f-c70b974759af"));
        qrCodeRequest.setCurrentDateTime(LocalDateTime.parse("2024-09-23T19:00:00"));

        // when + then
        mockMvc.perform(post("/qr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(qrCodeRequest)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.username").value("jan"))
//                .andExpect(jsonPath("$.email").value("jan@example.com"));
    }
}