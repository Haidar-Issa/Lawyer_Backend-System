package com.web.lawyer_backend_system.dto.opposingParity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpposingPartyResponseDto {

    private String opposingPartyId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String lawyerName;
    private String lawyerPhone;
    private String notes;

    private String caseId;
    private String caseTitle;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
