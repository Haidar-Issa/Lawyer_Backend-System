package com.web.lawyer_backend_system.dto.cases;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseResponseDto {

    private String caseId;
    private String title;
    private String description;
    private String caseStatus;
    private String courtName;
    private String caseNumber;
    private String creator;
    private String assignedLawyer;
    private String client;
    private LocalDateTime startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
