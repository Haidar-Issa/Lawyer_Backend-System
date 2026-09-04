package com.web.lawyer_backend_system.mapper.courtSession;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionRequestDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionResponseDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionUpdateRequestDto;
import com.web.lawyer_backend_system.entity.CourtSession;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CourtSessionMapper {
    @Mapping(target = "courtSessionId", ignore = true)
    @Mapping(target = "legalCase", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CourtSession toEntity(CourtSessionRequestDto dto);

    @Mapping(source = "legalCase.caseId", target = "caseId")
    @Mapping(source = "legalCase.title", target = "caseTitle")
    CourtSessionResponseDto toResponseDto(CourtSession entity);

    @Mapping(target = "courtSessionId", ignore = true)
    @Mapping(target = "legalCase", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(CourtSessionUpdateRequestDto dto, @MappingTarget CourtSession entity);
}
