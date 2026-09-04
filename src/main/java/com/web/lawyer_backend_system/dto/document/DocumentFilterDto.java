package com.web.lawyer_backend_system.dto.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFilterDto {
    private String searchTitle;
    private String fileName;
    private String caseId;
    private String uploaderId;
}
