package com.example.danceschool.participant;

import com.example.danceschool.course.CourseNotFoundException;
import com.example.danceschool.user.UserNotFoundException;
import com.example.danceschool.course.Course;
import com.example.danceschool.user.User;
import com.example.danceschool.course.CourseRepository;
import com.example.danceschool.user.UserRepository;
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
    private final ParticipantMapper participantMapper;

    public Optional<ParticipantDto> createParticipantForCourseIfNotAlready(UUID courseId, UUID userId) {
        Optional<Participant> participantOptional = participantRepository.findByCourseIdAndUserId(courseId, userId);
        if (participantOptional.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(createParticipant(courseId, userId));
    }

    public ParticipantDto createParticipant(UUID courseId, UUID userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Participant participant = new Participant();
        participant.setUser(user);
        participant.setCourse(course);

        participantRepository.save(participant);

        return participantMapper.toDto(participant);
    }
}