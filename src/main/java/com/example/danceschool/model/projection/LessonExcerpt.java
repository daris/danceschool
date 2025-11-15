package com.example.danceschool.model.projection;

import com.example.danceschool.model.Lesson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Projection(name = "lessonExcerpt", types = Lesson.class)
public interface LessonExcerpt {
    UUID getId();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    List<AttendanceExcerpt> getAttendances();

    @Value("#{target.course.id}")
    UUID getCourseId();
}

