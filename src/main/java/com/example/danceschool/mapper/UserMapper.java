package com.example.danceschool.mapper;

import com.example.danceschool.dto.UserDto;
import com.example.danceschool.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserPassMapper.class})
public interface UserMapper {

    UserDto toDto(User user);
}