package com.web.lawyer_backend_system.controller.presigned;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.document.DocumentFilterDto;
import com.web.lawyer_backend_system.dto.document.DocumentRequestDto;
import com.web.lawyer_backend_system.dto.document.DocumentResponseDto;
import com.web.lawyer_backend_system.dto.document.DocumentUpdateRequestDto;
import com.web.lawyer_backend_system.dto.presigned.PresignedUrlRequestDto;
import com.web.lawyer_backend_system.dto.presigned.PresignedUrlResponseDto;
import com.web.lawyer_backend_system.service.document.DocumentService;
import com.web.lawyer_backend_system.service.presigned.S3StorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final S3StorageService s3StorageService;

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping("/presigned-url")
    public ResponseEntity<ApiResponse<PresignedUrlResponseDto>> generatePresignedUrl(
            @Valid @RequestBody PresignedUrlRequestDto requestDto,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Presigned URL generated successfully",
                        s3StorageService.generatePresignedUrl(requestDto),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<DocumentResponseDto>> createDocument(
            @Valid @RequestBody DocumentRequestDto requestDto,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Document record saved successfully",
                        documentService.createDocument(requestDto),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentResponseDto>> getDocumentById(
            @PathVariable String id,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Document retrieved successfully",
                        documentService.getDocumentById(id),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentResponseDto>> updateDocument(
            @PathVariable String id,
            @Valid @RequestBody DocumentUpdateRequestDto updateRequestDto,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Document updated successfully",
                        documentService.updateDocument(id, updateRequestDto),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(
            @PathVariable String id,
            HttpServletRequest request) {

        documentService.deleteDocument(id);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Document deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<DocumentResponseDto>>> searchDocuments(
            @ModelAttribute DocumentFilterDto filterDto,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Documents retrieved successfully using filter criteria",
                        documentService.filterDocuments(filterDto, pageable),
                        request.getRequestURI()
                )
        );
    }
}
