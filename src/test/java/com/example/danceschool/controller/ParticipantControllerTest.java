package com.example.danceschool.controller;

import com.example.danceschool.model.Course;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.UserRepository;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({BaseControllerTest.TestConfig.class})
@Transactional
public class ParticipantControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private UUID userId;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        setUpCurrentUserAdmin();

        // Create & save user
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        userRepository.save(user);
        userId = user.getId();

        // Create & save course
        Course course = new Course();
        course.setName("Test Course");
        courseRepository.save(course);
        courseId = course.getId();
    }

    @Test
    void shouldCreateParticipantSuccessfully() throws Exception {
        ParticipantController.ParticipantRequest request = new ParticipantController.ParticipantRequest();
        request.setUserId(userId);
        request.setCourseId(courseId);

        mockMvc.perform(post("/api/participants")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.courseId").value(courseId.toString()));
    }

    @Test
    void shouldReturn400WhenMissingData() throws Exception {
        ParticipantController.ParticipantRequest request = new ParticipantController.ParticipantRequest(); // Missing userId & courseId

        mockMvc.perform(post("/api/participants")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}