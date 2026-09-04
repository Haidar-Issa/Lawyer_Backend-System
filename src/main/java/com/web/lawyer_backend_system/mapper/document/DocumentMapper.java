package com.web.lawyer_backend_system.mapper.document;
import com.web.lawyer_backend_system.dto.document.DocumentRequestDto;
import com.web.lawyer_backend_system.dto.document.DocumentResponseDto;
import com.web.lawyer_backend_system.dto.document.DocumentUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Document;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DocumentMapper {
    @Mapping(target = "documentId", ignore = true)
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "uploader", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Document toEntity(DocumentRequestDto dto);

    @Mapping(source = "caseId.caseId", target = "caseId")
    @Mapping(source = "caseId.title", target = "caseTitle")
    @Mapping(source = "uploader.userId", target = "uploaderId")
    @Mapping(source = "uploader.fullName", target = "uploaderName")
    DocumentResponseDto toResponseDto(Document entity);

    @Mapping(target = "documentId", ignore = true)
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "uploader", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(DocumentUpdateRequestDto dto, @MappingTarget Document entity);
}
