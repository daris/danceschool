package com.example.danceschool.auth;

import com.example.danceschool.event.UserEvent;
import com.example.danceschool.exception.InvalidCredentialsException;
import com.example.danceschool.jwt.JwtService;
import com.example.danceschool.service.CustomUserDetailsService;
import com.example.danceschool.service.KafkaProducer;
import com.example.danceschool.user.User;
import com.example.danceschool.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private KafkaProducer producer;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_savesUserAndSendsKafkaEvent() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("password");
        request.setEmail("john@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        authService.registerUser(request);

        // Verify user saved
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("john", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals("USER", savedUser.getRole());

        // Verify Kafka event sent
        verify(producer, times(1)).sendMessage(eq("user-created"), any(UserEvent.class));
    }

    @Test
    void loginUser_success_returnsTokenAndUser() {
        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("password");

        User mockUser = new User();
        mockUser.setUsername("john");

        when(userDetailsService.loadUserByUsername("john")).thenReturn(mockUser);
        when(jwtService.generateToken("john")).thenReturn("jwt-token");

        LoginResponse response = authService.loginUser(request);

        assertEquals("jwt-token", response.getAccessToken());
        assertEquals(mockUser, response.getUser());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginUser_invalidCredentials_throwsException() {
        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("wrong");

        // Use BadCredentialsException instead of abstract AuthenticationException
        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(InvalidCredentialsException.class, () -> authService.loginUser(request));
    }
}