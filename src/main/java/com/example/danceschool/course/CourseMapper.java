package com.example.danceschool.course;

import com.example.danceschool.lesson.LessonMapper;
import com.example.danceschool.mapper.ParticipantMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { LessonMapper.class, ParticipantMapper.class })
public interface CourseMapper {
    CourseDto toDto(Course course);
}