package com.example.danceschool.service;

import com.example.danceschool.dto.AttendanceDto;
import com.example.danceschool.dto.websocket.CourseAttendanceUpdateMessage;
import com.example.danceschool.dto.CourseDto;
import com.example.danceschool.dto.internal.SetAttendanceStatusDto;
import com.example.danceschool.exception.CourseNotFoundException;
import com.example.danceschool.mapper.CourseMapper;
import com.example.danceschool.model.Course;
import com.example.danceschool.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final AttendanceService attendanceService;
    private final SimpMessagingTemplate messagingTemplate;
    private final CourseRepository courseRepository;
    private final ParticipantService participantService;
    private final CourseMapper courseMapper;

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .toList();
    }

    public CourseDto getCourseById(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        return courseMapper.toDto(course);
    }

    public CourseDto getCourseForLesson(UUID lessonId) {
        Course course = courseRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found for lesson with id: " + lessonId));
        return courseMapper.toDto(course);
    }

    public CourseDto createCourse(String name, String level) {
        Course course = new Course();
        course.setName(name);
        course.setLevel(level);

        courseRepository.save(course);

        return courseMapper.toDto(course);
    }

    public AttendanceDto setAttendanceStatusForLesson(SetAttendanceStatusDto dto) {
        CourseDto course = getCourseForLesson(dto.getLessonId());

        if (dto.isCreateParticipant()) {
            participantService.createParticipantForCourseIfNotAlready(course.getId(), dto.getUserId());
        }

        AttendanceDto attendance = attendanceService.setAttendanceStatusForLesson(dto.getLessonId(), dto.getUserId(), dto.getStatus());

        CourseAttendanceUpdateMessage updateDto = new CourseAttendanceUpdateMessage(course.getId(), attendance.getId(), dto.getLessonId(), dto.getUserId(), dto.getStatus());
        notifyCourseAttendancesChanged(updateDto);

        return attendance;
    }

    private void notifyCourseAttendancesChanged(CourseAttendanceUpdateMessage dto) {
        String topic = "/topic/courses/" + dto.getCourseId() + "/attendances";
        messagingTemplate.convertAndSend(topic, dto);
    }
}
