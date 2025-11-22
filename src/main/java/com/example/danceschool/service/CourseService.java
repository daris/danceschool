package com.example.danceschool.service;

import com.example.danceschool.dto.CourseAttendancesUpdateDto;
import com.example.danceschool.dto.SetAttendanceStatusDto;
import com.example.danceschool.exception.CourseNotFoundException;
import com.example.danceschool.model.Attendance;
import com.example.danceschool.model.Course;
import com.example.danceschool.model.Lesson;
import com.example.danceschool.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final AttendanceService attendanceService;
    private final SimpMessagingTemplate messagingTemplate;
    private final CourseRepository courseRepository;
    private final ParticipantService participantService;

    public Course getCourseById(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
    }

    public Course getCourseForLesson(UUID lessonId) {
        return courseRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found for lesson with id: " + lessonId));
    }

    public Attendance setAttendanceStatusForLesson(SetAttendanceStatusDto dto) {
        Course course = getCourseForLesson(dto.getLessonId());

        if (dto.isCreateParticipant()) {
            participantService.createParticipantForCourseIfNotAlready(course.getId(), dto.getUserId());
        }

        Attendance attendance = attendanceService.setAttendanceStatusForLesson(dto.getLessonId(), dto.getUserId(), dto.getStatus());

        CourseAttendancesUpdateDto updateDto = new CourseAttendancesUpdateDto(course.getId(), attendance.getId(), dto.getLessonId(), dto.getUserId(), dto.getStatus());
        notifyCourseAttendancesChanged(updateDto);

        return attendance;
    }

    private void notifyCourseAttendancesChanged(CourseAttendancesUpdateDto dto) {
        String topic = "/topic/courses/" + dto.getCourseId() + "/attendances";
        messagingTemplate.convertAndSend(topic, dto);
    }
}
