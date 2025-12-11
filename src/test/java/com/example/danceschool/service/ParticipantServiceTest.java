package com.example.danceschool.service;

import com.example.danceschool.dto.ParticipantDto;
import com.example.danceschool.exception.CourseNotFoundException;
import com.example.danceschool.exception.UserNotFoundException;
import com.example.danceschool.mapper.ParticipantMapper;
import com.example.danceschool.course.Course;
import com.example.danceschool.model.Participant;
import com.example.danceschool.user.User;
import com.example.danceschool.course.CourseRepository;
import com.example.danceschool.repository.ParticipantRepository;
import com.example.danceschool.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ParticipantMapper participantMapper;

    @InjectMocks
    private ParticipantService participantService;

    private UUID courseId;
    private UUID userId;
    private Course course;
    private User user;
    private Participant participant;
    private ParticipantDto participantDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        courseId = UUID.randomUUID();
        userId = UUID.randomUUID();

        course = new Course();
        course.setId(courseId);

        user = new User();
        user.setId(userId);

        participant = new Participant();
        participant.setCourse(course);
        participant.setUser(user);

        participantDto = new ParticipantDto();
        participantDto.setId(UUID.randomUUID());
    }

    @Test
    void shouldCreateParticipantWhenNotExists() {
        // Given
        when(participantRepository.findByCourseIdAndUserId(courseId, userId)).thenReturn(Optional.empty());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(participantMapper.toDto(any(Participant.class))).thenReturn(participantDto);

        // When
        Optional<ParticipantDto> result = participantService.createParticipantForCourseIfNotAlready(courseId, userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(participantDto.getId(), result.get().getId());
        verify(participantRepository, times(1)).save(any(Participant.class));
    }

    @Test
    void shouldReturnEmptyWhenParticipantAlreadyExists() {
        // Given
        when(participantRepository.findByCourseIdAndUserId(courseId, userId)).thenReturn(Optional.of(participant));

        // When
        Optional<ParticipantDto> result = participantService.createParticipantForCourseIfNotAlready(courseId, userId);

        // Then
        assertTrue(result.isEmpty());
        verify(participantRepository, never()).save(any(Participant.class));
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        // Given
        when(participantRepository.findByCourseIdAndUserId(courseId, userId)).thenReturn(Optional.empty());
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CourseNotFoundException.class,
                () -> participantService.createParticipant(courseId, userId));
        verify(participantRepository, never()).save(any(Participant.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(participantRepository.findByCourseIdAndUserId(courseId, userId)).thenReturn(Optional.empty());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class,
                () -> participantService.createParticipant(courseId, userId));
        verify(participantRepository, never()).save(any(Participant.class));
    }
}