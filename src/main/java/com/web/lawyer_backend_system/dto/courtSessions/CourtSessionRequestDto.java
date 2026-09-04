package com.web.lawyer_backend_system.dto.courtSessions;
import com.web.lawyer_backend_system.enums.CourtSessionStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CourtSessionRequestDto {
    @NotNull(message = "Session date is required")
    @FutureOrPresent(message = "Session date must be in the present or future")
    private LocalDate sessionDate;

    private LocalTime sessionTime;

    private String courtRoom;

    private String judgeName;

    private String decision;

    private LocalDate decisionDate;

    private CourtSessionStatus status;

    @NotBlank(message = "Case ID is required")
    private String caseId;

    private List<String> notes;
}
