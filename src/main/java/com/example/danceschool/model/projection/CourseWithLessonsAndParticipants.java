package com.example.danceschool.model.projection;

import com.example.danceschool.model.Course;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;
import java.util.UUID;

@Projection(name = "withLessonsAndParticipants", types = Course.class)
public interface CourseWithLessonsAndParticipants {
    UUID getId();
    String getName();
    String getLevel();

    List<LessonExcerpt> getLessons();
    List<ParticipantExcerpt> getParticipants();
}