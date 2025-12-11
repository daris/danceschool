package com.example.danceschool.userpass;

import com.example.danceschool.course.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserPassMapper {

    public UserPassDto toDto(UserPass userPass) {
        UserPassDto dto = new UserPassDto();
        dto.setId(userPass.getId());
        dto.setStartTime(userPass.getStartTime());
        dto.setEndTime(userPass.getEndTime());
        dto.setCreatedAt(userPass.getCreatedAt());

        if (userPass.getCourses() != null) {
            List<UUID> courseIds = userPass.getCourses()
                    .stream()
                    .map(Course::getId)
                    .collect(Collectors.toList());
            dto.setCourseIds(courseIds);
        }

        return dto;
    }

}