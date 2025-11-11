package com.example.danceschool.controller;

import com.example.danceschool.model.Course;
import com.example.danceschool.repository.CourseRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RepositoryRestController
public class CourseRestController {

    private final CourseRepository courseRepository;

    public CourseRestController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses/with-participants")
    public ResponseEntity<List<Course>> getCoursesWithParticipants() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(c -> c.getParticipants().size()); // force load
        return ResponseEntity.ok(courses);
    }
}