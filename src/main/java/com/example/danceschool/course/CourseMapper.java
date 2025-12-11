package com.example.danceschool.course;

import com.example.danceschool.lesson.LessonDto;
import com.example.danceschool.lesson.LessonMapper;
import com.example.danceschool.participant.ParticipantDto;
import com.example.danceschool.participant.ParticipantMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    private final LessonMapper lessonMapper;
    private final ParticipantMapper participantMapper;

    public CourseMapper(LessonMapper lessonMapper, ParticipantMapper participantMapper) {
        this.lessonMapper = lessonMapper;
        this.participantMapper = participantMapper;
    }

    public CourseDto toDto(Course course) {
        if (course == null) return null;

        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setLevel(course.getLevel());

        if (course.getLessons() != null) {
            List<LessonDto> lessons = course.getLessons().stream()
                    .map(lessonMapper::toDto)
                    .collect(Collectors.toList());
            dto.setLessons(lessons);
        }

        if (course.getParticipants() != null) {
            List<ParticipantDto> participants = course.getParticipants().stream()
                    .map(participantMapper::toDto)
                    .collect(Collectors.toList());
            dto.setParticipants(participants);
        }

        return dto;
    }
}