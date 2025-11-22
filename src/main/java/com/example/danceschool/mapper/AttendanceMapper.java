package com.example.danceschool.mapper;

import com.example.danceschool.dto.AttendanceDto;
import com.example.danceschool.model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "userId", source = "user.id")
    AttendanceDto toDto(Attendance attendance);
}