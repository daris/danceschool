package com.example.danceschool.repository;

import com.example.danceschool.model.ScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, UUID> {

    List<ScheduleEntry> findByDayOfWeekAndStartTimeAfterAndEndTimeBefore(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime);
}