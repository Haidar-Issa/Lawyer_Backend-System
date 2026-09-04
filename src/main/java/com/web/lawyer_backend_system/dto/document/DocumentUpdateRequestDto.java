package com.web.lawyer_backend_system.dto.document;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUpdateRequestDto {
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String description;

    private String fileName;

    private String fileUrl;

    private String caseId;

    private String uploaderId;

}
