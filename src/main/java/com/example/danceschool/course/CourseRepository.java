package com.example.danceschool.course;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query("SELECT c FROM Course c JOIN c.lessons l WHERE l.id = :lessonId")
    Optional<Course> findByLessonId(@Param("lessonId") UUID lessonId);

    @Override
    @EntityGraph(value = "course-with-lessons")
    List<Course> findAll();
}
