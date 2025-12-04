package com.example.danceschool.mapper;

import com.example.danceschool.dto.CourseDto;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CourseMapperSpringTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void toDto_mapsSimpleFields() {
        // given
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setName("Hip Hop");
        course.setLevel("Beginner");

        // when
        CourseDto dto = courseMapper.toDto(course);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(course.getId());
        assertThat(dto.getName()).isEqualTo("Hip Hop");
        assertThat(dto.getLevel()).isEqualTo("Beginner");
    }

    @Test
    void toDto_mapsNestedLessons() {
        // given
        Course course = new Course();
        course.setId(UUID.randomUUID());

        Lesson l1 = new Lesson();
        l1.setId(UUID.randomUUID());

        Lesson l2 = new Lesson();
        l2.setId(UUID.randomUUID());

        course.setLessons(List.of(l1, l2));

        // when
        CourseDto dto = courseMapper.toDto(course);

        // then
        assertThat(dto.getLessons()).hasSize(2);
        assertThat(dto.getLessons())
                .extracting("id")
                .containsExactly(l1.getId(), l2.getId());
    }

    @Test
    void toDto_returnsNull_whenInputIsNull() {
        assertThat(courseMapper.toDto(null)).isNull();
    }
}
