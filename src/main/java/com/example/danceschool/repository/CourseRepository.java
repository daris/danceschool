package com.example.danceschool.repository;

import com.example.danceschool.model.Course;
import com.example.danceschool.model.projection.CourseWithLessonsAndParticipants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(
        path = "courses",
        excerptProjection = CourseWithLessonsAndParticipants.class
)
public interface CourseRepository extends JpaRepository<Course, UUID> {
}
