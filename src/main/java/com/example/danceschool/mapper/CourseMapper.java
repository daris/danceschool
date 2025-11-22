package com.example.danceschool.mapper;

import com.example.danceschool.dto.CourseDto;
import com.example.danceschool.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { LessonMapper.class, ParticipantMapper.class })
public interface CourseMapper {
    CourseDto toDto(Course course);
}