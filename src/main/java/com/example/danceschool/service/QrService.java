package com.example.danceschool.service;

import com.example.danceschool.dto.*;
import com.example.danceschool.dto.request.QrCodeRequest;
import com.example.danceschool.dto.response.QrCodeResponse;
import com.example.danceschool.exception.UnknownQrCodeType;
import com.example.danceschool.model.AttendanceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrService {
    private final CourseService courseService;

    public QrCodeResponse handleQrCode(QrCodeRequest qrCodeRequest, UUID userId) {
        if (qrCodeRequest.getType() == QrCodeType.LESSON) {
            UUID lessonId = qrCodeRequest.getId();

            SetAttendanceStatusDto dto = new SetAttendanceStatusDto();
            dto.setLessonId(lessonId);
            dto.setUserId(userId);
            dto.setStatus(AttendanceStatus.NORMAL);
            courseService.setAttendanceStatusForLesson(dto);

            CourseDto course = courseService.getCourseForLesson(lessonId);

            QrCodeResponse response = new QrCodeResponse();
            response.setMessage("Registered attendance for a course: " + course.getName());

            return response;
        }

        throw new UnknownQrCodeType("Unknown QR Code type");
    }
}
