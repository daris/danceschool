package com.example.danceschool.service;

import com.example.danceschool.model.ScheduleEntry;
import com.example.danceschool.model.User;
import com.example.danceschool.model.UserCard;
import com.example.danceschool.repository.ScheduleEntryRepository;
import com.example.danceschool.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DummyDataService {

    private final ScheduleEntryRepository scheduleEntryRepository;
    private final UserRepository userRepository;

    public DummyDataService(ScheduleEntryRepository scheduleEntryRepository, UserRepository userRepository) {
        this.scheduleEntryRepository = scheduleEntryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void importSchedule() {
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
    }

    @Transactional
    public void importUsers() {
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
    }
}