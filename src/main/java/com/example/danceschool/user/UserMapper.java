package com.example.danceschool.user;

import com.example.danceschool.userpass.UserPassMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public final UserPassMapper userPassMapper;

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());

        if (user.getPasses() != null) {
            dto.setPasses(
                    user.getPasses()
                            .stream()
                            .map(userPassMapper::toDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}