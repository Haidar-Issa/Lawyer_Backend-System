package com.web.lawyer_backend_system.dto.invoice;

import com.web.lawyer_backend_system.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDto {
    private String invoiceId;
    private Client client;
    private BigInteger invoiceNumber;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private String status;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
