package com.example.danceschool.model.projection;

import com.example.danceschool.model.Course;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;


@Projection(name = "withParticipants", types = Course.class)
public interface CourseWithParticipants {
//    UUID getId();
//    String getName();
    String getLevel();
    List<ParticipantExcerpt> getParticipants();
}