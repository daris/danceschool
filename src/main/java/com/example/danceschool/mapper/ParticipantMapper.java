package com.example.danceschool.mapper;

import com.example.danceschool.dto.ParticipantDto;
import com.example.danceschool.model.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "courseId", source = "course.id")
    ParticipantDto toDto(Participant participant);

    List<ParticipantDto> toDtoList(List<Participant> participants);
}