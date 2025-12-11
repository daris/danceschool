package com.example.danceschool.attendance;

import com.example.danceschool.dto.AttendanceDto;
import com.example.danceschool.model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "userId", source = "user.id")
    AttendanceDto toDto(Attendance attendance);
}