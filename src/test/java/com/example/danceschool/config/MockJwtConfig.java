package com.example.danceschool.config;

import com.example.danceschool.jwt.JwtService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockJwtConfig {

    @Bean
    @Primary   // overrides the real JwtService in tests
    public JwtService mockJwtService() {
        return Mockito.mock(JwtService.class);
    }
}