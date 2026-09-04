package com.web.lawyer_backend_system.dto.caseNote;
import com.web.lawyer_backend_system.dto.caseNoteHistory.CaseNoteHistoryResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CaseNoteResponseDto {
    private String caseNoteId;
    private String text;
    private String caseId;
    private String createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<CaseNoteHistoryResponseDto> history;
}
