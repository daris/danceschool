package com.example.danceschool.model.projection;

import com.example.danceschool.model.User;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "userExcerpt", types = User.class)
public interface UserExcerpt {
    UUID getId();
    String getFirstName();
    String getLastName();
}