package com.web.lawyer_backend_system.service.presigned;

import com.web.lawyer_backend_system.dto.presigned.PresignedUrlRequestDto;
import com.web.lawyer_backend_system.dto.presigned.PresignedUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageService {
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name:lawyer-documents-bucket}")
    private String bucketName;

    @Value("${aws.s3.region:us-east-1}")
    private String region;

    public PresignedUrlResponseDto generatePresignedUrl(PresignedUrlRequestDto request) {
        String uniqueFileName = UUID.randomUUID() + "_" + request.getFileName();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFileName)
                .contentType(request.getContentType())
                .build();


        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .putObjectRequest(objectRequest)
                .build();

        String uploadUrl = s3Presigner.presignPutObject(presignRequest).url().toString();
        String permanentFileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, uniqueFileName);

        return PresignedUrlResponseDto.builder()
                .uploadUrl(uploadUrl)
                .fileKey(uniqueFileName)
                .fileUrl(permanentFileUrl)
                .build();
    }
}
