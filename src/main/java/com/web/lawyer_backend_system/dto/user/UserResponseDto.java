package com.web.lawyer_backend_system.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class UserResponseDto {
    private String userId;
    private String fullName;
    private String email;
    private BigInteger phoneNumber;
    private BigInteger nationalNumber;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
