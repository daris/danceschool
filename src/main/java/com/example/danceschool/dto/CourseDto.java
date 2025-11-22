package com.example.danceschool.dto;

import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.Participant;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CourseDto {
    private UUID id;
    private String name;
    private String level;
    private List<LessonDto> lessons;
    private List<ParticipantDto> participants;
}