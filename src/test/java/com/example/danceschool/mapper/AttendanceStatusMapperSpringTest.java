package com.example.danceschool.mapper;

import com.example.danceschool.dto.internal.SetAttendanceStatusDto;
import com.example.danceschool.dto.request.AttendanceStatusRequest;
import com.example.danceschool.model.AttendanceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AttendanceStatusMapperSpringTest {

    @Autowired
    private AttendanceStatusMapper mapper;

    @Test
    void toSetStatusDto_mapsCorrectly() {
        UUID lessonId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        AttendanceStatusRequest request = new AttendanceStatusRequest();
        request.setLessonId(lessonId);
        request.setUserId(userId);
        request.setStatus(AttendanceStatus.NORMAL);

        SetAttendanceStatusDto dto = mapper.toSetStatusDto(request);

        assertThat(dto.getLessonId()).isEqualTo(lessonId);
        assertThat(dto.getUserId()).isEqualTo(userId);
        assertThat(dto.getStatus()).isEqualTo(AttendanceStatus.NORMAL);
        assertThat(dto.isCreateParticipant()).isTrue(); // constant value
    }
}
