package com.example.danceschool.controller;

import com.example.danceschool.config.MockJwtConfig;
import com.example.danceschool.config.MockUserDetailsServiceConfig;
import com.example.danceschool.dto.QrCodeRequest;
import com.example.danceschool.jwt.JwtService;
import com.example.danceschool.model.*;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.LessonRepository;
import com.example.danceschool.repository.ScheduleEntryRepository;
import com.example.danceschool.repository.UserRepository;
import com.example.danceschool.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({MockJwtConfig.class, MockUserDetailsServiceConfig.class})
@Transactional // rollback after each test
class QrCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleEntryRepository scheduleEntryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    String authToken = "fake.jwt.token";

    void setUpCurrentUser() {
        when(jwtService.extractUsername(authToken)).thenReturn("john");
        when(jwtService.isTokenValid(authToken, "john")).thenReturn(true);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("john")
                .password("{noop}pass")
                .roles("USER")
                .build();

        when(userDetailsService.loadUserByUsername("john")).thenReturn(userDetails);
    }

    @BeforeEach
    void setUp() {
        setUpCurrentUser();
    }

    @Test
    void testQr() throws Exception {
        // given
        List<User> users = new ArrayList<>();
        User user;

        user = new User();
        user.setUsername("jan");
        user.setEmail("jan@example.com");
        users.add(user);
        userRepository.saveAll(users);

        Course course = new Course();
        course.setName("Course 1");
        courseRepository.save(course);

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lessonRepository.save(lesson);

        QrCodeRequest qrCodeRequest = new QrCodeRequest();
        qrCodeRequest.setId(lesson.getId());

        // when + then
        mockMvc.perform(post("/qr")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(qrCodeRequest)))
                .andExpect(status().isOk());
    }
}