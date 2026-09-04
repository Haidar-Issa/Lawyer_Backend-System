package com.web.lawyer_backend_system.dto.courtSessions;
import com.web.lawyer_backend_system.enums.CourtSessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourtSessionUpdateRequestDto {
    private LocalDate sessionDate;

    private LocalTime sessionTime;

    private String courtRoom;

    private String judgeName;

    private String decision;

    private LocalDate decisionDate;

    private CourtSessionStatus status;

    private String caseId;

    private List<String> notes;
}
