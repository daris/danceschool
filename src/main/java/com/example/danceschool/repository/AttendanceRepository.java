package com.example.danceschool.repository;

import com.example.danceschool.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findByLessonIdAndUserId(UUID lessonId, UUID user);
}