package com.web.lawyer_backend_system.mapper.caseNote;


import com.web.lawyer_backend_system.dto.caseNote.CaseNoteRequestDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteResponseDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteUpdateDto;
import com.web.lawyer_backend_system.entity.CaseNote;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CaseNoteMapper {

    @Mapping(target = "caseNoteId", ignore = true)
    @Mapping(target = "legalCase", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "history", ignore = true)
    CaseNote toEntity(CaseNoteRequestDto dto);

    @Mapping(source = "legalCase.caseId", target = "caseId")
    @Mapping(source = "creator.userId", target = "createdById")
    @Mapping(source = "creator.fullName", target = "createdByName")
    CaseNoteResponseDto toResponseDto(CaseNote entity);

    @Mapping(target = "caseNoteId", ignore = true)
    @Mapping(target = "legalCase", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "history", ignore = true)
    void updateEntityFromDto(CaseNoteUpdateDto dto, @MappingTarget CaseNote entity);

}
