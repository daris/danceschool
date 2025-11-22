package com.example.danceschool.service;

import com.example.danceschool.dto.CourseAttendancesUpdateDto;
import com.example.danceschool.model.*;
import com.example.danceschool.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final ParticipantRepository participantRepository;
    private final AttendanceService attendanceService;
    private final SimpMessagingTemplate messagingTemplate;

    public void registerAttendanceForCourse(Course course, Lesson lesson, User user) {
        createParticipantForCourseIfNotAlready(course, user);
        Attendance attendance = attendanceService.setAttendanceStatusForLesson(lesson, user, AttendanceStatus.NORMAL);

        notifyCourseAttendancesChanged(lesson, user, attendance);
    }

    public Attendance setAttendanceStatusForLesson(Lesson lesson, User user, AttendanceStatus status) {
        Attendance attendance = attendanceService.setAttendanceStatusForLesson(lesson, user, status);
        notifyCourseAttendancesChanged(lesson, user, attendance);

        return attendance;
    }

    private void createParticipantForCourseIfNotAlready(Course course, User user) {
        boolean isParticipating = course.getParticipants().stream().anyMatch(participant -> participant.getUser().getId().equals(user.getId()));
        if (!isParticipating) {
            Participant participant = new Participant();
            participant.setUser(user);
            participant.setCourse(course);
            participantRepository.save(participant);
        }
    }

    private void notifyCourseAttendancesChanged(Lesson lesson, User user, Attendance attendance) {
        CourseAttendancesUpdateDto courseAttendancesUpdateDto = new CourseAttendancesUpdateDto();
        courseAttendancesUpdateDto.setCourseId(lesson.getCourse().getId());
        courseAttendancesUpdateDto.setAttendanceId(attendance.getId());
        courseAttendancesUpdateDto.setLessonId(lesson.getId());
        courseAttendancesUpdateDto.setUserId(user.getId());
        courseAttendancesUpdateDto.setStatus(attendance.getStatus());

        String topic = "/topic/courses/" + lesson.getCourse().getId() + "/attendances";
        messagingTemplate.convertAndSend(topic, courseAttendancesUpdateDto);
    }
}
