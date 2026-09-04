package com.web.lawyer_backend_system.dto.caseNote;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CaseNoteFilterDto {
    private String CaseNoteId;
    private String caseId;
    private String createdById;
    private String searchText;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}
