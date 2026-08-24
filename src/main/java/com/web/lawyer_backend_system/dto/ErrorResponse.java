package com.web.lawyer_backend_system.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@RequiredArgsConstructor
@Setter
@Getter
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    @Setter
    private List<String> details;

    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponse(int status, String message, LocalDateTime timestamp, java.util.List<String> details) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

}
