package com.example.danceschool.service;

import com.example.danceschool.model.ScheduleEntry;
import com.example.danceschool.repository.ScheduleEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleEntryService {

    private final ScheduleEntryRepository scheduleEntryRepository;

    public ScheduleEntryService(ScheduleEntryRepository scheduleEntryRepository) {
        this.scheduleEntryRepository = scheduleEntryRepository;
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
        scheduleEntry.setStartTime(LocalTime.parse("17:00"));
        scheduleEntry.setEndTime(LocalTime.parse("18:00"));
        scheduleEntry.setStartDate(LocalDate.parse("2025-01-01"));
        scheduleEntries.add(scheduleEntry);

        scheduleEntryRepository.saveAll(scheduleEntries);
    }
}