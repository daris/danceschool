package com.example.danceschool.controller;

import com.example.danceschool.model.Course;
import com.example.danceschool.repository.CourseRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({BaseControllerTest.TestConfig.class})
@Transactional
public class LessonControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    private UUID courseId;
    private Instant startTime;
    private Instant endTime;

    @BeforeEach
    void setUp() {
        setUpCurrentUserAdmin();

        // Create & save course
        Course course = new Course();
        course.setName("Test Course");
        courseRepository.save(course);
        courseId = course.getId();

        startTime = Instant.now();
        endTime = startTime.plusSeconds(3600);
    }

    @Test
    void shouldCreateLessonSuccessfully() throws Exception {

        LessonController.LessonRequest request = new LessonController.LessonRequest();
        request.setCourseId(courseId);
        request.setStartTime(startTime);
        request.setEndTime(endTime);

        mockMvc.perform(post("/api/lessons")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.startTime").value(startTime.toString()))
                .andExpect(jsonPath("$.endTime").value(endTime.toString()));
    }

    @Test
    void shouldReturn400WhenMissingData() throws Exception {
        LessonController.LessonRequest request = new LessonController.LessonRequest(); // empty request

        mockMvc.perform(post("/api/lessons")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}