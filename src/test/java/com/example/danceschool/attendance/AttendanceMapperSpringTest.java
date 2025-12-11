package com.example.danceschool.attendance;

import com.example.danceschool.lesson.Lesson;
import com.example.danceschool.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AttendanceMapperSpringTest {

    @Autowired
    private AttendanceMapper mapper;

    @Test
    void toDto_mapsCorrectly() {
        UUID lessonId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Lesson lesson = new Lesson();
        lesson.setId(lessonId);

        User user = new User();
        user.setId(userId);

        Attendance attendance = new Attendance();
        attendance.setId(UUID.randomUUID());
        attendance.setStatus(AttendanceStatus.NORMAL);
        attendance.setLesson(lesson);
        attendance.setUser(user);

        AttendanceDto dto = mapper.toDto(attendance);

        assertThat(dto.getId()).isEqualTo(attendance.getId());
        assertThat(dto.getLessonId()).isEqualTo(lessonId);
        assertThat(dto.getUserId()).isEqualTo(userId);
        assertThat(dto.getStatus()).isEqualTo(AttendanceStatus.NORMAL);
    }
}
