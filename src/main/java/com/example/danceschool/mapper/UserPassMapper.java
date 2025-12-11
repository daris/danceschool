package com.example.danceschool.mapper;

import com.example.danceschool.course.Course;
import com.example.danceschool.dto.UserPassDto;
import com.example.danceschool.model.UserPass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserPassMapper {

    UserPassMapper INSTANCE = Mappers.getMapper(UserPassMapper.class);

    @Mapping(target = "courseIds", source = "courses", qualifiedByName = "mapCourseIds")
    UserPassDto toDto(UserPass userPass);

    @Named("mapCourseIds")
    default List<java.util.UUID> mapCourseIds(List<Course> courses) {
        return courses.stream().map(Course::getId).collect(Collectors.toList());
    }
}