package com.example.danceschool.participant;

import com.example.danceschool.course.Course;
import com.example.danceschool.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ParticipantMapperTest {

    @Autowired
    private ParticipantMapper mapper;

    @Test
    void toDto_mapsNestedIdsCorrectly() {
        // given
        UUID participantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);

        Course course = new Course();
        course.setId(courseId);

        Participant participant = new Participant();
        participant.setId(participantId);
        participant.setUser(user);
        participant.setCourse(course);

        // when
        ParticipantDto dto = mapper.toDto(participant);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(participantId);
        assertThat(dto.getUserId()).isEqualTo(userId);
        assertThat(dto.getCourseId()).isEqualTo(courseId);
    }

    @Test
    void toDto_returnsNull_whenInputNull() {
        assertThat(mapper.toDto(null)).isNull();
    }
}
