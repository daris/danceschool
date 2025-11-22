package com.example.danceschool.service;

import com.example.danceschool.model.Course;
import com.example.danceschool.model.Participant;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.ParticipantRepository;
import com.example.danceschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public void createParticipantForCourseIfNotAlready(UUID courseId, UUID userId) {
        Optional<Participant> participantOptional = participantRepository.findByCourseIdAndUserId(courseId, userId);
        if (participantOptional.isPresent()) {
            return;
        }

        createParticipant(courseId, userId);
    }

    private void createParticipant(UUID courseId, UUID userId) {
        Course courseProxy = courseRepository.getReferenceById(courseId);
        User userProxy = userRepository.getReferenceById(userId);

        Participant participant = new Participant();
        participant.setCourse(courseProxy);
        participant.setUser(userProxy);
        participantRepository.save(participant);
    }
}