package com.example.danceschool.attendance;

import com.example.danceschool.dto.internal.SetAttendanceStatusDto;
import com.example.danceschool.dto.request.AttendanceStatusRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceStatusMapper {

    @Mapping(target = "createParticipant", constant = "true")
    SetAttendanceStatusDto toSetStatusDto(AttendanceStatusRequest request);
}