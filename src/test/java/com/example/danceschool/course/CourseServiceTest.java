package com.example.danceschool.course;

import com.example.danceschool.attendance.AttendanceService;
import com.example.danceschool.dto.AttendanceDto;
import com.example.danceschool.dto.websocket.CourseAttendanceUpdateMessage;
import com.example.danceschool.dto.internal.SetAttendanceStatusDto;
import com.example.danceschool.exception.CourseNotFoundException;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    @Mock
    private AttendanceService attendanceService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ParticipantService participantService;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private UUID courseId;
    private UUID lessonId;
    private UUID userId;
    private Course course;
    private CourseDto courseDto;
    private AttendanceDto attendanceDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        lessonId = UUID.randomUUID();
        userId = UUID.randomUUID();

        course = new Course();
        course.setId(courseId);
        course.setName("Salsa");
        course.setLevel("Beginner");

        courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setName("Salsa");
        courseDto.setLevel("Beginner");

        attendanceDto = new AttendanceDto();
        attendanceDto.setId(UUID.randomUUID());
    }

    @Test
    void shouldReturnAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        List<CourseDto> result = courseService.getAllCourses();

        assertEquals(1, result.size());
        assertEquals("Salsa", result.get(0).getName());
        verify(courseRepository).findAll();
        verify(courseMapper).toDto(course);
    }

    @Test
    void shouldReturnCourseById() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        CourseDto result = courseService.getCourseById(courseId);

        assertNotNull(result);
        assertEquals(courseId, result.getId());
        verify(courseRepository).findById(courseId);
        verify(courseMapper).toDto(course);
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
    }

    @Test
    void shouldCreateCourse() {
        courseDto.setLevel("Intermediate");

        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> {
            Course saved = invocation.getArgument(0);
            saved.setId(courseId);
            return saved;
        });
        when(courseMapper.toDto(any(Course.class))).thenReturn(courseDto);

        CourseDto result = courseService.createCourse("Salsa", "Intermediate");

        assertNotNull(result.getId());
        assertEquals("Salsa", result.getName());
        assertEquals("Intermediate", result.getLevel());
        verify(courseRepository).save(any(Course.class));
        verify(courseMapper).toDto(any(Course.class));
    }

    @Test
    void shouldSetAttendanceStatusAndNotify() {
        // Given
        SetAttendanceStatusDto dto = new SetAttendanceStatusDto(null, lessonId, userId, null, true);
        dto.setStatus(AttendanceStatus.NORMAL);

        when(courseRepository.findByLessonId(lessonId)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseDto);
        when(attendanceService.setAttendanceStatusForLesson(lessonId, userId, dto.getStatus()))
                .thenReturn(attendanceDto);

        // When
        AttendanceDto result = courseService.setAttendanceStatusForLesson(dto);

        // Then
        verify(participantService).createParticipantForCourseIfNotAlready(courseId, userId);
        verify(attendanceService).setAttendanceStatusForLesson(lessonId, userId, dto.getStatus());

        ArgumentCaptor<CourseAttendanceUpdateMessage> captor = ArgumentCaptor.forClass(CourseAttendanceUpdateMessage.class);
        verify(messagingTemplate).convertAndSend(eq("/topic/courses/" + courseId + "/attendances"), captor.capture());

        assertEquals(attendanceDto.getId(), captor.getValue().getAttendanceId());
        assertEquals(attendanceDto, result);
    }
}