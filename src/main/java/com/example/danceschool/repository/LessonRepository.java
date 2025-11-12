package com.example.danceschool.repository;

import com.example.danceschool.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}