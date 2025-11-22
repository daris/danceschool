package com.example.danceschool.service;

import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.AttendanceRepository;
import com.example.danceschool.repository.LessonRepository;
import com.example.danceschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public Attendance setAttendanceStatusForLesson(UUID lessonId, UUID userId, AttendanceStatus status) {
        Optional<Attendance> existingAttendance = attendanceRepository.findByLessonIdAndUserId(lessonId, userId);
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(status);
            attendanceRepository.save(attendance);

            return attendance;
        }

        return createAttendance(lessonId, userId, status);
    }

    private Attendance createAttendance(UUID lessonId, UUID userId, AttendanceStatus status) {
        Lesson lessonProxy = lessonRepository.getReferenceById(lessonId);
        User userProxy = userRepository.getReferenceById(userId);

        Attendance attendance = new Attendance();
        attendance.setLesson(lessonProxy);
        attendance.setUser(userProxy);
        attendance.setStatus(status);
        attendanceRepository.save(attendance);
        return attendance;
    }
}
