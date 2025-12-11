package com.example.danceschool.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("john_doe");
        user.setEmail("john@example.com");

        userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
    }

    @Test
    void shouldReturnAllUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        List<UserDto> result = userService.getAllUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        assertEquals("john@example.com", result.get(0).getEmail());
        assertEquals(user.getId(), result.get(0).getId());
    }
}