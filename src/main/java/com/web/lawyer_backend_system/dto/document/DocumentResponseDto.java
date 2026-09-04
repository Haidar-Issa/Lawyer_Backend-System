package com.web.lawyer_backend_system.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDto {
    private String documentId;
    private String title;
    private String description;
    private String fileName;
    private String fileUrl;

    private String caseId;
    private String caseTitle;

    private String uploaderId;
    private String uploaderName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
