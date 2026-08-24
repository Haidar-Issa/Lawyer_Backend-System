package com.web.lawyer_backend_system.dto;

import lombok.Builder;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;
    private String message;
    private T data;
    private String path;
    private LocalDateTime timestamp;

    public static <T> ApiResponse <T> create(HttpStatus status,String message,T data ,String path){
        return  ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

}
