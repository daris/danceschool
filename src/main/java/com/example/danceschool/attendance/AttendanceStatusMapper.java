package com.example.danceschool.attendance;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceStatusMapper {

    @Mapping(target = "createParticipant", constant = "true")
    SetAttendanceStatusDto toSetStatusDto(AttendanceStatusRequest request);
}