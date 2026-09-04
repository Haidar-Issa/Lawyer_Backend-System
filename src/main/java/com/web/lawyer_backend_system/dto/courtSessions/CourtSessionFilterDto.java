package com.web.lawyer_backend_system.dto.courtSessions;
import com.web.lawyer_backend_system.enums.CourtSessionStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CourtSessionFilterDto {
    private String searchJudgeName;
    private String courtRoom;
    private CourtSessionStatus status;
    private String caseId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate sessionDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate sessionDateTo;
}
