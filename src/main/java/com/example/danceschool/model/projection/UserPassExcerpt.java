package com.example.danceschool.model.projection;

import com.example.danceschool.model.Course;
import com.example.danceschool.model.UserPass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Projection(name = "userPassExcerpt", types = UserPass.class)
public interface UserPassExcerpt {
    UUID getId();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();

    @Value("#{target.courses.![id]}")
    List<UUID> getCourseIds();

}