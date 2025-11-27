package com.example.danceschool.service;

import com.example.danceschool.dto.LessonDto;
import com.example.danceschool.exception.CourseNotFoundException;
import com.example.danceschool.mapper.LessonMapper;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonService lessonService;

    private UUID courseId;
    private Course course;
    private Lesson lesson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        course = new Course();
        course.setId(courseId);

        lesson = new Lesson();
        lesson.setCourse(course);
    }

    @Test
    void shouldCreateLessonSuccessfully() {
        // Given
        Instant startTime = Instant.now();
        Instant endTime = startTime.plusSeconds(3600);
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(UUID.randomUUID());

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(lessonDto);

        // When
        LessonDto result = lessonService.createLesson(startTime, endTime, courseId);

        // Then
        assertNotNull(result);
        assertEquals(lessonDto.getId(), result.getId());

        verify(courseRepository, times(1)).findById(courseId);
        verify(lessonRepository, times(1)).save(any(Lesson.class));
        verify(lessonMapper, times(1)).toDto(lesson);
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        // Given
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // When & Then
        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class,
                () -> lessonService.createLesson(Instant.now(), Instant.now().plusSeconds(3600), courseId));

        assertTrue(exception.getMessage().contains(courseId.toString()));
        verify(lessonRepository, never()).save(any(Lesson.class));
        verify(lessonMapper, never()).toDto(any(Lesson.class));
    }
}