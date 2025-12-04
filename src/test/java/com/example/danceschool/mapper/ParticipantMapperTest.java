package com.example.danceschool.mapper;

import com.example.danceschool.dto.ParticipantDto;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Participant;
import com.example.danceschool.model.User;
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

    @Test
    void toDtoList_mapsListCorrectly() {
        // given
        Participant p1 = new Participant();
        p1.setId(UUID.randomUUID());
        User u1 = new User(); u1.setId(UUID.randomUUID());
        Course c1 = new Course(); c1.setId(UUID.randomUUID());
        p1.setUser(u1);
        p1.setCourse(c1);

        Participant p2 = new Participant();
        p2.setId(UUID.randomUUID());
        User u2 = new User(); u2.setId(UUID.randomUUID());
        Course c2 = new Course(); c2.setId(UUID.randomUUID());
        p2.setUser(u2);
        p2.setCourse(c2);

        List<Participant> list = List.of(p1, p2);

        // when
        List<ParticipantDto> dtos = mapper.toDtoList(list);

        // then
        assertThat(dtos).hasSize(2);
        assertThat(dtos)
                .extracting(ParticipantDto::getId)
                .containsExactly(p1.getId(), p2.getId());
    }

    @Test
    void toDtoList_returnsNull_whenInputNull() {
        assertThat(mapper.toDtoList(null)).isNull();
    }
}
