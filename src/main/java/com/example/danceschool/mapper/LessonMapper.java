package com.example.danceschool.mapper;

import com.example.danceschool.attendance.AttendanceMapper;
import com.example.danceschool.dto.LessonDto;
import com.example.danceschool.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AttendanceMapper.class })
public interface LessonMapper {
    @Mapping(target = "attendances", source = "attendances")
    LessonDto toDto(Lesson lesson);

    List<LessonDto> toDtoList(List<Lesson> lessons);
}