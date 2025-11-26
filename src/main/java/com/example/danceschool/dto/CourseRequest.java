package com.example.danceschool.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank
    private String name;

    private String level;
}