package com.web.lawyer_backend_system.dto.invoice;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class InvoiceRequestDto {
    private BigDecimal amount;
    private String currency;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private BigDecimal paidAmount;
    private String caseId;
    private String client;
    private String notes;
}
