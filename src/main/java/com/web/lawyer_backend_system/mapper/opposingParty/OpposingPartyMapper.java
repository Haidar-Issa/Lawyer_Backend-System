package com.web.lawyer_backend_system.mapper.opposingParty;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyRequestDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyResponseDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyUpdateRequestDto;
import com.web.lawyer_backend_system.entity.OpposingParity;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OpposingPartyMapper {
    @Mapping(target = "opposingPartyId", ignore = true)
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OpposingParity toEntity(OpposingPartyRequestDto dto);

    @Mapping(source = "opposingPartyId", target = "opposingPartyId")
    @Mapping(source = "caseId.caseId", target = "caseId")
    @Mapping(source = "caseId.title", target = "caseTitle")
    OpposingPartyResponseDto toResponseDto(OpposingParity entity);

    @Mapping(target = "opposingPartyId", ignore = true)
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(OpposingPartyUpdateRequestDto dto, @MappingTarget OpposingParity entity);
}
