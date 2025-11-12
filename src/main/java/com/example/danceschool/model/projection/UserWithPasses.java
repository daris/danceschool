package com.example.danceschool.model.projection;

import com.example.danceschool.model.Course;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;
import java.util.UUID;

@Projection(name = "withPasses", types = Course.class)
public interface UserWithPasses {
    UUID getId();
    String getFirstName();
    String getLastName();

    List<UserPassExcerpt> getPasses();
}