package com.web.lawyer_backend_system.mapper.caseNoteHistory;

import com.web.lawyer_backend_system.dto.caseNoteHistory.CaseNoteHistoryResponseDto;
import com.web.lawyer_backend_system.entity.CaseNoteHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface CaseNoteHistoryMapper {
    @Mapping(source = "changedBy.userId", target = "changedById")
    @Mapping(source = "changedBy.fullName", target = "changedByName")
    CaseNoteHistoryResponseDto toHistoryResponseDto(CaseNoteHistory entity);
}
