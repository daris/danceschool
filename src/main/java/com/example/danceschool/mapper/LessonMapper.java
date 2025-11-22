package com.example.danceschool.mapper;

import com.example.danceschool.dto.LessonDto;
import com.example.danceschool.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AttendanceMapper.class })
public interface LessonMapper {

    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    @Mapping(target = "attendances", source = "attendances")
    LessonDto toDto(Lesson lesson);

    List<LessonDto> toDtoList(List<Lesson> lessons);

    // Optionally, map from DTO back to entity
    Lesson toEntity(LessonDto dto);
}