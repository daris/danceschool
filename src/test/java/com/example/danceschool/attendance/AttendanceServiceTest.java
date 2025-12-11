package com.example.danceschool.attendance;

import com.example.danceschool.dto.AttendanceDto;
import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.user.User;
import com.example.danceschool.repository.LessonRepository;
import com.example.danceschool.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AttendanceMapper attendanceMapper;

    @InjectMocks
    private AttendanceService attendanceService;

    private UUID lessonId;
    private UUID userId;
    private Attendance attendance;
    private AttendanceDto attendanceDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lessonId = UUID.randomUUID();
        userId = UUID.randomUUID();

        attendance = new Attendance();
        attendance.setStatus(AttendanceStatus.NORMAL);

        attendanceDto = new AttendanceDto();
        attendanceDto.setStatus(AttendanceStatus.NORMAL);
    }

    @Test
    void shouldUpdateExistingAttendance() {
        // Given
        when(attendanceRepository.findByLessonIdAndUserId(lessonId, userId))
                .thenReturn(Optional.of(attendance));
        when(attendanceMapper.toDto(attendance)).thenReturn(attendanceDto);

        // When
        AttendanceDto result = attendanceService.setAttendanceStatusForLesson(lessonId, userId, AttendanceStatus.FULL_PASS);

        // Then
        verify(attendanceRepository).save(attendance);
        verify(attendanceMapper).toDto(attendance);
        assertEquals(AttendanceStatus.FULL_PASS, attendance.getStatus());
        assertEquals(attendanceDto, result);
    }

    @Test
    void shouldCreateAttendanceIfNotExists() {
        // Given
        when(attendanceRepository.findByLessonIdAndUserId(lessonId, userId))
                .thenReturn(Optional.empty());

        Lesson lesson = new Lesson();
        User user = new User();
        when(lessonRepository.getReferenceById(lessonId)).thenReturn(lesson);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        when(attendanceMapper.toDto(any(Attendance.class))).thenReturn(attendanceDto);

        // When
        AttendanceDto result = attendanceService.setAttendanceStatusForLesson(lessonId, userId, AttendanceStatus.NORMAL);

        // Then
        verify(lessonRepository).getReferenceById(lessonId);
        verify(userRepository).getReferenceById(userId);
        verify(attendanceRepository).save(any(Attendance.class));
        verify(attendanceMapper).toDto(any(Attendance.class));

        assertEquals(attendanceDto, result);
    }

    @Test
    void createAttendanceShouldSaveCorrectly() {
        // Given
        Lesson lesson = new Lesson();
        User user = new User();
        when(lessonRepository.getReferenceById(lessonId)).thenReturn(lesson);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        when(attendanceMapper.toDto(any(Attendance.class))).thenReturn(attendanceDto);

        // When
        AttendanceDto result = attendanceService.createAttendance(lessonId, userId, AttendanceStatus.NORMAL);

        // Then
        ArgumentCaptor<Attendance> captor = ArgumentCaptor.forClass(Attendance.class);
        verify(attendanceRepository).save(captor.capture());
        verify(attendanceMapper).toDto(any(Attendance.class));

        Attendance savedAttendance = captor.getValue();
        assertEquals(lesson, savedAttendance.getLesson());
        assertEquals(user, savedAttendance.getUser());
        assertEquals(AttendanceStatus.NORMAL, savedAttendance.getStatus());
        assertEquals(attendanceDto, result);
    }
}