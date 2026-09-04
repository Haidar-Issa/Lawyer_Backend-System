package com.web.lawyer_backend_system.service.document;

import com.web.lawyer_backend_system.dto.document.DocumentFilterDto;
import com.web.lawyer_backend_system.dto.document.DocumentRequestDto;
import com.web.lawyer_backend_system.dto.document.DocumentResponseDto;
import com.web.lawyer_backend_system.dto.document.DocumentUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentService {
    DocumentResponseDto createDocument(DocumentRequestDto dto);

    DocumentResponseDto getDocumentById(String id);

    DocumentResponseDto updateDocument(String id, DocumentUpdateRequestDto dto);

    void deleteDocument(String id);

    Page<DocumentResponseDto> filterDocuments(DocumentFilterDto filterDto, Pageable pageable);
}
