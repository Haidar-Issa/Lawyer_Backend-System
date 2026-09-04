package com.web.lawyer_backend_system.service.document;
import com.web.lawyer_backend_system.dto.document.DocumentFilterDto;
import com.web.lawyer_backend_system.dto.document.DocumentRequestDto;
import com.web.lawyer_backend_system.dto.document.DocumentResponseDto;
import com.web.lawyer_backend_system.dto.document.DocumentUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.Document;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.document.DocumentMapper;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.DocumentRepository;
import com.web.lawyer_backend_system.repository.UserRepository;
import com.web.lawyer_backend_system.repository.specification.DocumentSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentServiceImpl implements DocumentService{
    private final DocumentRepository documentRepository;
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    @Override
    public DocumentResponseDto createDocument(DocumentRequestDto dto) {
        Case_ legalCase = caseRepository.findById(dto.getCaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + dto.getCaseId()));

        Document document = documentMapper.toEntity(dto);
        document.setCaseId(legalCase);

        if (StringUtils.hasText(dto.getUploaderId())) {
            User uploader = userRepository.findById(dto.getUploaderId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUploaderId()));
            document.setUploader(uploader);
        }

        Document savedDocument = documentRepository.save(document);
        return documentMapper.toResponseDto(savedDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponseDto getDocumentById(String id) {
        Document document = findById(id);
        return documentMapper.toResponseDto(document);
    }

    @Override
    public DocumentResponseDto updateDocument(String id, DocumentUpdateRequestDto dto) {
        Document existingDocument = findById(id);

        documentMapper.updateEntityFromDto(dto, existingDocument);

        if (StringUtils.hasText(dto.getCaseId())) {
            Case_ legalCase = caseRepository.findById(dto.getCaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + dto.getCaseId()));
            existingDocument.setCaseId(legalCase);
        }

        if (StringUtils.hasText(dto.getUploaderId())) {
            User uploader = userRepository.findById(dto.getUploaderId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUploaderId()));
            existingDocument.setUploader(uploader);
        }

        Document updatedDocument = documentRepository.save(existingDocument);
        return documentMapper.toResponseDto(updatedDocument);
    }

    @Override
    public void deleteDocument(String id) {
        Document document = findById(id);
        documentRepository.delete(document);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentResponseDto> filterDocuments(DocumentFilterDto filterDto, Pageable pageable) {
        Specification<Document> spec = DocumentSpecifications.build(filterDto);
        return documentRepository.findAll(spec, pageable).map(documentMapper::toResponseDto);
    }

    private Document findById(String id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));
    }
}
