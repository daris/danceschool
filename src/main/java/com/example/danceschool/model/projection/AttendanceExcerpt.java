package com.example.danceschool.model.projection;

import com.example.danceschool.model.Attendance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "attendanceExcerpt", types = Attendance.class)
public interface AttendanceExcerpt {
    UUID getId();
    String getStatus();

    @Value("#{target.user.id}")
    UUID getUserId();
}