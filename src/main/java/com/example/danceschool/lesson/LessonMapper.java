package com.example.danceschool.lesson;

import com.example.danceschool.attendance.AttendanceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AttendanceMapper.class })
public interface LessonMapper {
    @Mapping(target = "attendances", source = "attendances")
    LessonDto toDto(Lesson lesson);

    List<LessonDto> toDtoList(List<Lesson> lessons);
}