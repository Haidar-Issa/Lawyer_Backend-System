package com.web.lawyer_backend_system.dto.cases;

import com.web.lawyer_backend_system.enums.CaseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CaseFilterDto {
    private String searchKeyword;
    private CaseStatus caseStatus;
    private String courtName;
    private String caseNumber;
    private String creatorId;
    private String assignedLawyerId;
    private String clientId;
    private LocalDateTime startDateFrom;
    private LocalDateTime startDateTo;
}
