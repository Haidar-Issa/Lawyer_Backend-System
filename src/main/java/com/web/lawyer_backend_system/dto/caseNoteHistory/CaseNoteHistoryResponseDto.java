package com.web.lawyer_backend_system.dto.caseNoteHistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CaseNoteHistoryResponseDto {

    private String historyId;
    private String oldText;
    private String newText;
    private String changedById;
    private String changedByName;
    private LocalDateTime changedAt;
}
