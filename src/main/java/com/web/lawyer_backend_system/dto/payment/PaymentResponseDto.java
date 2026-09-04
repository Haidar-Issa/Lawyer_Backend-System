package com.web.lawyer_backend_system.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponseDto {
    String paymentId;
    BigDecimal amount;
    String paymentMethod;
    LocalDateTime paymentDate;
    String note;
    String invoice;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
