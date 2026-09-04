package com.web.lawyer_backend_system.dto.presigned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresignedUrlResponseDto {
    private String uploadUrl;
    private String fileKey;
    private String fileUrl;
}
