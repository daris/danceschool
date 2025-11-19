package com.example.danceschool.service;

import com.example.danceschool.model.Course;
import com.example.danceschool.model.Participant;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final ParticipantRepository participantRepository;

    public CourseService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void createParticipantForCourseIfNotAlready(Course course, User user) {
        boolean isParticipating = course.getParticipants().stream().anyMatch(participant -> participant.getUser().getId().equals(user.getId()));
        if (!isParticipating) {
            Participant participant = new Participant();
            participant.setUser(user);
            participant.setCourse(course);
            participantRepository.save(participant);
        }
    }
}
