package com.example.danceschool.repository;

import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.Participant;
import com.example.danceschool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findByLessonIsAndUserIs(Lesson lesson, User user);
}