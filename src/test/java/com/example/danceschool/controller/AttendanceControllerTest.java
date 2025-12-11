package com.example.danceschool.controller;

import com.example.danceschool.dto.request.AttendanceStatusRequest;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.user.User;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.LessonRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({BaseControllerTest.TestConfig.class})
@Transactional
class AttendanceControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        setUpCurrentUserAdmin();
    }

    @Test
    void shouldReturnAttendance() throws Exception {

        Course course = new Course();
        course.setName("Course 1");
        courseRepository.save(course);

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setStartTime(Instant.now());
        lesson.setEndTime(Instant.now());
        lessonRepository.save(lesson);

        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.com");
        userRepository.save(user);

        AttendanceStatusRequest request = new AttendanceStatusRequest();
        request.setLessonId(lesson.getId());
        request.setUserId(user.getId());
        request.setStatus(AttendanceStatus.NORMAL);

        mockMvc.perform(post("/api/attendances/set-status")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("NORMAL"))
                .andExpect(jsonPath("$.userId").value(user.getId().toString()))
                .andExpect(jsonPath("$.lessonId").value(lesson.getId().toString()));
    }
}