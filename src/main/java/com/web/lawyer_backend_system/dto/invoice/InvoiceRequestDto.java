package com.web.lawyer_backend_system.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequestDto {
    private BigDecimal amount;
    private String currency;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private BigDecimal Amount;
    private BigDecimal paidAmount;
    private String client;
    private String notes;
}
