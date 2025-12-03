package com.example.danceschool.service;

import com.example.danceschool.dto.*;
import com.example.danceschool.dto.internal.SetAttendanceStatusDto;
import com.example.danceschool.dto.request.QrCodeRequest;
import com.example.danceschool.dto.response.QrCodeResponse;
import com.example.danceschool.exception.UnknownQrCodeType;
import com.example.danceschool.model.AttendanceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QrServiceTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private QrService qrService;

    private UUID userId;
    private UUID lessonId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        lessonId = UUID.randomUUID();
    }

    @Test
    void handleQrCode_validLessonType_registersAttendanceAndReturnsMessage() {
        // Given
        QrCodeRequest qrRequest = new QrCodeRequest();
        qrRequest.setType(QrCodeType.LESSON);
        qrRequest.setId(lessonId);

        // Mock course response
        CourseDto courseDto = new CourseDto();
        courseDto.setId(UUID.randomUUID());
        courseDto.setName("Hip-Hop Beginners");

        when(courseService.getCourseForLesson(lessonId)).thenReturn(courseDto);

        // When
        QrCodeResponse response = qrService.handleQrCode(qrRequest, userId);

        // Then
        ArgumentCaptor<SetAttendanceStatusDto> captor = ArgumentCaptor.forClass(SetAttendanceStatusDto.class);
        verify(courseService, times(1)).setAttendanceStatusForLesson(captor.capture());

        SetAttendanceStatusDto dto = captor.getValue();
        assertEquals(lessonId, dto.getLessonId());
        assertEquals(userId, dto.getUserId());
        assertEquals(AttendanceStatus.NORMAL, dto.getStatus());

        assertEquals("Registered attendance for a course: Hip-Hop Beginners", response.getMessage());
    }

    @Test
    void handleQrCode_invalidType_throwsUnknownQrCodeType() {
        // Given
        QrCodeRequest qrRequest = new QrCodeRequest();
        qrRequest.setType(null); // or use another invalid value

        // Then
        assertThrows(UnknownQrCodeType.class,
                () -> qrService.handleQrCode(qrRequest, userId));

        verifyNoInteractions(courseService);
    }
}