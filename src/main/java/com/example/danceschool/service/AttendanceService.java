package com.example.danceschool.service;

import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final LessonService lessonService;
    private final UserService userService;

    public Attendance setAttendanceStatusForLesson(UUID lessonId, UUID userId, AttendanceStatus status) {
        Lesson lesson = lessonService.getById(lessonId);
        User user = userService.getById(userId);

        Optional<Attendance> existingAttendance = attendanceRepository.findByLessonIdAndUserId(lessonId, userId);
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(status);
            attendanceRepository.save(attendance);

            return attendance;
        }

        return createAttendance(lesson, user, status);
    }

    private Attendance createAttendance(Lesson lesson, User user, AttendanceStatus status) {
        Attendance attendance = new Attendance();
        attendance.setLesson(lesson);
        attendance.setUser(user);
        attendance.setStatus(status);
        attendanceRepository.save(attendance);
        return attendance;
    }
}
