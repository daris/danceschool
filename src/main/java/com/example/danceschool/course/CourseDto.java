package com.example.danceschool.course;

import com.example.danceschool.lesson.LessonDto;
import com.example.danceschool.dto.ParticipantDto;
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