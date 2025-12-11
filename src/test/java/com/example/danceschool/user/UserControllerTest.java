package com.example.danceschool.user;

import com.example.danceschool.controller.BaseControllerTest;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.UserPass;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.UserPassRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({BaseControllerTest.TestConfig.class})
@Transactional
public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserPassRepository userPassRepository;

    private UUID userId;
    private UUID courseId;
    private UUID passId;

    @BeforeEach
    void setUp() {
        setUpCurrentUserAdmin(); // creates 'jan' as admin

        // Create course
        Course course = new Course();
        course.setName("Test Course");
        courseRepository.save(course);
        courseId = course.getId();

        // Create user
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        userRepository.save(user);
        userId = user.getId();

        // Create user pass
        UserPass pass = new UserPass();
        pass.setUser(user);
        pass.setStartTime(LocalDateTime.now());
        pass.setEndTime(LocalDateTime.now().plusDays(30));
        pass.setCourses(List.of(course));
        userPassRepository.save(pass);
        passId = pass.getId();

        user.getPasses().add(pass);
    }

    @Test
    void shouldReturnAllUsersWithPasses() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer dummy-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // Find john_doe specifically
                .andExpect(jsonPath("$[?(@.username=='john_doe')].id").value(userId.toString()))
                .andExpect(jsonPath("$[?(@.username=='john_doe')].email").value("john@example.com"))
                .andExpect(jsonPath("$[?(@.username=='john_doe')].passes[0].id").value(passId.toString()))
                .andExpect(jsonPath("$[?(@.username=='john_doe')].passes[0].courseIds[0]").value(courseId.toString()));
    }
}