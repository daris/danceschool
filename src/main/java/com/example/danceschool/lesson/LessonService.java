package com.example.danceschool.lesson;

import com.example.danceschool.course.CourseNotFoundException;
import com.example.danceschool.course.Course;
import com.example.danceschool.course.CourseRepository;
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