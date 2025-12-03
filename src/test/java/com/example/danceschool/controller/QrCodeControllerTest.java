package com.example.danceschool.controller;

import com.example.danceschool.dto.request.QrCodeRequest;
import com.example.danceschool.dto.QrCodeType;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.LessonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({BaseControllerTest.TestConfig.class})
@Transactional // rollback after each test
class QrCodeControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
        setUpCurrentUser();
    }

    @Test
    void testQr() throws Exception {
        // given
        Course course = new Course();
        course.setName("Course 1");
        courseRepository.save(course);

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setStartTime(Instant.now());
        lesson.setEndTime(Instant.now());
        lessonRepository.save(lesson);

        QrCodeRequest qrCodeRequest = new QrCodeRequest();
        qrCodeRequest.setId(lesson.getId());
        qrCodeRequest.setType(QrCodeType.LESSON);

        // when + then
        mockMvc.perform(post("/api/qr")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(qrCodeRequest)))
                .andExpect(status().isOk());
    }
}