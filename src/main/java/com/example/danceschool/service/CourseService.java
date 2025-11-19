package com.example.danceschool.service;

import com.example.danceschool.dto.CourseAttendancesUpdateDto;
import com.example.danceschool.model.*;
import com.example.danceschool.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final ParticipantRepository participantRepository;
    private final AttendanceService attendanceService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public CourseService(ParticipantRepository participantRepository, AttendanceService attendanceService) {
        this.participantRepository = participantRepository;
        this.attendanceService = attendanceService;
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

    public void registerAttendanceForCourse(Course course, Lesson lesson, User user) {
        createParticipantForCourseIfNotAlready(course, user);
        Attendance attendance = attendanceService.setAttendanceStatusForLesson(lesson, user, AttendanceStatus.NORMAL);

        CourseAttendancesUpdateDto courseAttendancesUpdateDto = new CourseAttendancesUpdateDto();
        courseAttendancesUpdateDto.setCourseId(course.getId());
        courseAttendancesUpdateDto.setAttendanceId(attendance.getId());
        courseAttendancesUpdateDto.setLessonId(lesson.getId());
        courseAttendancesUpdateDto.setUserId(user.getId());
        courseAttendancesUpdateDto.setStatus(attendance.getStatus());

        String topic = "/topic/courses/" + course.getId() + "/attendances";
        messagingTemplate.convertAndSend(
                topic,
                courseAttendancesUpdateDto
        );
    }
}
