package com.example.danceschool.qr;

import com.example.danceschool.course.CourseDto;
import com.example.danceschool.course.CourseService;
import com.example.danceschool.attendance.SetAttendanceStatusDto;
import com.example.danceschool.attendance.AttendanceStatus;
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
