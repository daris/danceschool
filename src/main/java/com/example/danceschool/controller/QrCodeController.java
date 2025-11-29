package com.example.danceschool.controller;

import com.example.danceschool.component.SecurityUtils;
import com.example.danceschool.dto.ErrorResponse;
import com.example.danceschool.dto.QrCodeRequest;
import com.example.danceschool.dto.QrCodeResponse;
import com.example.danceschool.model.User;
import com.example.danceschool.service.QrService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class QrCodeController {
    private final SecurityUtils securityUtils;
    private final QrService qrService;

    @PostMapping("qr")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attendance successfully registered",
                    content = @Content(schema = @Schema(implementation = QrCodeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<QrCodeResponse> qr(@Valid @RequestBody QrCodeRequest qrCodeRequest) {
        User user = securityUtils.getCurrentUser();
        QrCodeResponse response = qrService.handleQrCode(qrCodeRequest, user.getId());
        return ResponseEntity.ok(response);
    }
}