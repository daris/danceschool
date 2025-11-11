package com.example.danceschool.repository;

import com.example.danceschool.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    @EntityGraph(attributePaths = {"lessons", "participants"})
    Optional<Course> findById(UUID id);
}