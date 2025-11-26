package com.example.danceschool.controller;

import com.example.danceschool.dto.CourseRequest;
import com.example.danceschool.model.*;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.LessonRepository;
import com.example.danceschool.repository.UserRepository;
import com.example.danceschool.repository.ParticipantRepository;
import com.example.danceschool.repository.AttendanceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({BaseControllerTest.TestConfig.class})
@Transactional
class CourseControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired CourseRepository courseRepository;
    @Autowired LessonRepository lessonRepository;
    @Autowired UserRepository userRepository;
    @Autowired ParticipantRepository participantRepository;
    @Autowired AttendanceRepository attendanceRepository;
    @Autowired private ObjectMapper objectMapper;

    private UUID courseId;
    private UUID lessonId;
    private UUID participantId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        setUpCurrentUserAdmin();

        // Create Course
        Course course = new Course();
        course.setName("Test Course");
        course.setLevel("Beginner");
        courseRepository.save(course);
        this.courseId = course.getId();

        // Create Lesson
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setStartTime(Instant.now());
        lesson.setEndTime(Instant.now().plusSeconds(3600));
        lessonRepository.save(lesson);
        this.lessonId = lesson.getId();

        course.getLessons().add(lesson);

        // Create User
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        userRepository.save(user);
        this.userId = user.getId();

        // Create Participant
        Participant participant = new Participant();
        participant.setCourse(course);
        participant.setUser(user);
        participantRepository.save(participant);
        this.participantId = participant.getId();

        course.getParticipants().add(participant);

        // Create Attendance
        Attendance attendance = new Attendance();
        attendance.setLesson(lesson);
        attendance.setUser(user);
        attendance.setStatus(AttendanceStatus.NORMAL);
        attendanceRepository.save(attendance);

        lesson.getAttendances().add(attendance);
    }

    @Test
    void shouldReturnCourseWithNestedData() throws Exception {
        mockMvc.perform(get("/api/courses/" + courseId)
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                // Course level
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value("Test Course"))
                .andExpect(jsonPath("$.level").value("Beginner"))

                // Lesson level
                .andExpect(jsonPath("$.lessons[0].id").value(lessonId.toString()))
                .andExpect(jsonPath("$.lessons[0].startTime").exists())
                .andExpect(jsonPath("$.lessons[0].endTime").exists())

                // Attendance inside lesson
                .andExpect(jsonPath("$.lessons[0].attendances[0].status").value("NORMAL"))
                .andExpect(jsonPath("$.lessons[0].attendances[0].userId").value(userId.toString()))
                .andExpect(jsonPath("$.lessons[0].attendances[0].lessonId").value(lessonId.toString()))

                // Participant
                .andExpect(jsonPath("$.participants[0].id").value(participantId.toString()))
                .andExpect(jsonPath("$.participants[0].userId").value(userId.toString()))
                .andExpect(jsonPath("$.participants[0].courseId").value(courseId.toString()));
    }

    @Test
    void shouldReturnListOfCourses() throws Exception {
        mockMvc.perform(get("/api/courses")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("Test Course"))
                .andExpect(jsonPath("$[0].lessons[0].id").value(lessonId.toString()))
                .andExpect(jsonPath("$[0].participants[0].id").value(participantId.toString()));
    }

    @Test
    void shouldReturn404WhenCourseNotFound() throws Exception {
        mockMvc.perform(get("/api/courses/" + UUID.randomUUID())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn403WhenNoToken() throws Exception {
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isForbidden());
    }


    @Test
    void shouldCreateCourseSuccessfully() throws Exception {
        CourseRequest request = new CourseRequest();
        request.setName("Beginner Salsa");
        request.setLevel("Beginner");

        mockMvc.perform(post("/api/courses")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Beginner Salsa"))
                .andExpect(jsonPath("$.level").value("Beginner"));
    }

    @Test
    void shouldReturnBadRequestWhenMissingName() throws Exception {
        CourseRequest request = new CourseRequest();
        request.setLevel("Beginner"); // Missing name

        mockMvc.perform(post("/api/courses")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}