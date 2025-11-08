package com.example.danceschool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String level;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleEntry> scheduleEntries = new ArrayList<>();

    public Course() {}

}