package com.web.lawyer_backend_system.mapper.appointment;

import com.web.lawyer_backend_system.dto.appointment.AppointmentRequestDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentResponseDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Appointment;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AppointmentMapper {

    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "type", source = "type")
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "lawyerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Appointment toEntity(AppointmentRequestDto dto);


    @Mapping(source = "type", target = "type")
    @Mapping(source = "caseId.caseId", target = "caseId")
    @Mapping(source = "caseId.title", target = "caseTitle")
    @Mapping(source = "clientId.clientId", target = "clientId")
    @Mapping(source = "clientId.fullName", target = "clientName")
    @Mapping(source = "lawyerId.userId", target = "lawyerId")
    @Mapping(source = "lawyerId.fullName", target = "lawyerName")
    AppointmentResponseDto toResponseDto(Appointment entity);

    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "type", source = "type")
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "lawyerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AppointmentUpdateRequestDto dto, @MappingTarget Appointment entity);
}
