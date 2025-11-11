package com.example.danceschool.model.projection;

import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.User;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "attendanceExcerpt", types = Attendance.class)
public interface AttendanceExcerpt {
    UUID getId();
    String getStatus();

    // Optional — to show user info
    // Instead of full user, just embed username or email
    UserExcerpt getUser();
}