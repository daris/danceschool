package com.example.danceschool.attendance;

import com.example.danceschool.lesson.Lesson;
import com.example.danceschool.user.User;
import com.example.danceschool.lesson.LessonRepository;
import com.example.danceschool.user.UserRepository;
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
    private final AttendanceMapper attendanceMapper;

    public AttendanceDto setAttendanceStatusForLesson(UUID lessonId, UUID userId, AttendanceStatus status) {
        Optional<Attendance> existingAttendance = attendanceRepository.findByLessonIdAndUserId(lessonId, userId);
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(status);
            attendanceRepository.save(attendance);

            return attendanceMapper.toDto(attendance);
        }

        return createAttendance(lessonId, userId, status);
    }

    public AttendanceDto createAttendance(UUID lessonId, UUID userId, AttendanceStatus status) {
        Lesson lessonProxy = lessonRepository.getReferenceById(lessonId);
        User userProxy = userRepository.getReferenceById(userId);

        Attendance attendance = new Attendance();
        attendance.setLesson(lessonProxy);
        attendance.setUser(userProxy);
        attendance.setStatus(status);
        attendanceRepository.save(attendance);

        return attendanceMapper.toDto(attendance);
    }
}
