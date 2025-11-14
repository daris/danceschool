package com.example.danceschool.config;

import com.example.danceschool.service.CustomUserDetailsService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockUserDetailsServiceConfig {

    @Bean
    @Primary
    public CustomUserDetailsService mockUserDetailsService() {
        return Mockito.mock(CustomUserDetailsService.class);
    }
}