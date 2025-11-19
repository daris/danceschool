package com.example.danceschool.service;

import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.AttendanceStatus;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.model.User;
import com.example.danceschool.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance setAttendanceStatusForLesson(Lesson lesson, User user, AttendanceStatus status) {

        Optional<Attendance> existingAttendance = attendanceRepository.findByLessonAndUser(lesson, user);
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            AttendanceStatus newStatus = attendance.getStatus() == AttendanceStatus.NORMAL ? AttendanceStatus.FULL_PASS : AttendanceStatus.NORMAL;

            attendance.setStatus(newStatus);
            attendanceRepository.save(attendance);

            return attendance;
        }

        Attendance attendance = new Attendance();
        attendance.setLesson(lesson);
        attendance.setUser(user);
        attendance.setStatus(status);
        attendanceRepository.save(attendance);

        return attendance;
    }
}
