package com.example.danceschool.config;

import com.example.danceschool.jwt.JwtAuthFilter;
import com.example.danceschool.jwt.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationEntryPoint entryPoint) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/ws/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**", "/courses/**", "/lessons/**", "/participants/**", "/attendances/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/**", "/courses/**", "/lessons/**", "/participants/**", "/attendances/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/**", "/courses/**", "/lessons/**", "/participants/**", "/attendances/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**", "/courses/**", "/lessons/**", "/participants/**", "/attendances/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(entryPoint)
//                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}