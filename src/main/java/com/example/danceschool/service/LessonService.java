package com.example.danceschool.service;

import com.example.danceschool.dto.LessonDto;
import com.example.danceschool.exception.CourseNotFoundException;
import com.example.danceschool.mapper.LessonMapper;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    public LessonDto createLesson(Instant startTime, Instant endTime, UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id " + courseId));

        Lesson lesson = new Lesson();
        lesson.setStartTime(startTime);
        lesson.setEndTime(endTime);
        lesson.setCourse(course);

        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }
}