package com.example.danceschool.user;

import com.example.danceschool.mapper.UserPassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserPassMapper.class})
public interface UserMapper {

    UserDto toDto(User user);
}