package com.web.lawyer_backend_system.dto.client;

import com.web.lawyer_backend_system.dto.user.UserResponseDto;
import com.web.lawyer_backend_system.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ClientResponseDto {
    private String clientId;
    private String fullName;
    private String email;
    private BigInteger phoneNumber;
    private String address;
    private BigInteger nationalNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponseDto userId;
}
