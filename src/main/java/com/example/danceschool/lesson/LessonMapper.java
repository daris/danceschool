package com.example.danceschool.lesson;

import com.example.danceschool.attendance.AttendanceDto;
import com.example.danceschool.attendance.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LessonMapper {

    private final AttendanceMapper attendanceMapper;

    public LessonDto toDto(Lesson lesson) {
        if (lesson == null) return null;

        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setStartTime(lesson.getStartTime());
        dto.setEndTime(lesson.getEndTime());

        List<AttendanceDto> attendanceDtos = lesson.getAttendances() != null
                ? lesson.getAttendances().stream()
                        .map(attendanceMapper::toDto)
                        .collect(Collectors.toList())
                : null;

        dto.setAttendances(attendanceDtos);

        return dto;
    }
}