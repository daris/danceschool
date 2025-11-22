package com.example.danceschool.service;

import com.example.danceschool.dto.CourseAttendancesUpdateDto;
import com.example.danceschool.dto.SetAttendanceStatusDto;
import com.example.danceschool.exception.CourseNotFoundException;
import com.example.danceschool.exception.ResourceNotFoundException;
import com.example.danceschool.model.*;
import com.example.danceschool.repository.CourseRepository;
import com.example.danceschool.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final ParticipantRepository participantRepository;
    private final AttendanceService attendanceService;
    private final SimpMessagingTemplate messagingTemplate;
    private final LessonService lessonService;
    private final CourseRepository courseRepository;
    private final UserService userService;

    public Course getCourseById(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
    }

    public Course getCourseForLesson(UUID lessonId) {
        Lesson lesson = lessonService.getById(lessonId);
        return lesson.getCourse();
    }

    public Attendance setAttendanceStatusForLesson(SetAttendanceStatusDto dto) {
        Course course = getCourseForLesson(dto.getLessonId());

        if (dto.isCreateParticipant()) {
            createParticipantForCourseIfNotAlready(course.getId(), dto.getUserId());
        }

        Attendance attendance = attendanceService.setAttendanceStatusForLesson(dto.getLessonId(), dto.getUserId(), dto.getStatus());

        CourseAttendancesUpdateDto updateDto = new CourseAttendancesUpdateDto(course.getId(), attendance.getId(), dto.getLessonId(), dto.getUserId(), dto.getStatus());
        notifyCourseAttendancesChanged(updateDto);

        return attendance;
    }

    private void createParticipantForCourseIfNotAlready(UUID courseId, UUID userId) {
        Course course = getCourseById(courseId);
        User user = userService.getById(userId);

        boolean isParticipating = course.getParticipants().stream().anyMatch(participant -> participant.getUser().getId().equals(user.getId()));
        if (!isParticipating) {
            Participant participant = new Participant();
            participant.setUser(user);
            participant.setCourse(course);
            participantRepository.save(participant);
        }
    }

    private void notifyCourseAttendancesChanged(CourseAttendancesUpdateDto dto) {
        String topic = "/topic/courses/" + dto.getCourseId() + "/attendances";
        messagingTemplate.convertAndSend(topic, dto);
    }
}
