package com.web.lawyer_backend_system.service.appointment;

import com.web.lawyer_backend_system.dto.appointment.AppointmentFilterDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentRequestDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentResponseDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Appointment;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.exception.BadRequestException;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.appointment.AppointmentMapper;
import com.web.lawyer_backend_system.repository.AppointmentRepository;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.ClientRepository;
import com.web.lawyer_backend_system.repository.UserRepository;
import com.web.lawyer_backend_system.repository.specification.AppointmentSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Repository
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CaseRepository caseRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto dto) {
        validateDates(dto.getStartDate(), dto.getEndDate());

        Appointment appointment = appointmentMapper.toEntity(dto);

        return check(appointment, dto.getCaseId(), dto.getClientId(), dto.getLawyerId());
    }


    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDto getAppointmentById(String id) {
        Appointment appointment = findById(id);
        return appointmentMapper.toResponseDto(appointment);
    }

    @Override
    public AppointmentResponseDto updateAppointment(String id, AppointmentUpdateRequestDto dto) {
        Appointment existingAppointment = findById(id);

        LocalDateTime newStart = dto.getStartDate() != null ? dto.getStartDate() : existingAppointment.getStartDate();
        LocalDateTime newEnd = dto.getEndDate() != null ? dto.getEndDate() : existingAppointment.getEndDate();
        validateDates(newStart, newEnd);

        appointmentMapper.updateEntityFromDto(dto, existingAppointment);

        return check(existingAppointment, dto.getCaseId(), dto.getClientId(), dto.getLawyerId());
    }

    @Override
    public void deleteAppointment(String id) {
        Appointment appointment = findById(id);
        appointmentRepository.delete(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDto> filterAppointments(AppointmentFilterDto filterDto, Pageable pageable) {
        Specification<Appointment> spec = AppointmentSpecifications.build(filterDto);
        return appointmentRepository.findAll(spec, pageable).map(appointmentMapper::toResponseDto);
    }

    private Appointment findById(String id) {
        return appointmentRepository.findByAppointmentId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new BadRequestException("End date cannot be before start date");
        }
    }

    private AppointmentResponseDto check(Appointment appointment, String caseId, String clientId, String lawyerId) {
        if (StringUtils.hasText(caseId)) {
            Case_ legalCase = caseRepository.findById(caseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + caseId));
            appointment.setCaseId(legalCase);
        }

        if (StringUtils.hasText(clientId)) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + clientId));
            appointment.setClientId(client);
        }

        if (StringUtils.hasText(lawyerId)) {
            User lawyer = userRepository.findById(lawyerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Lawyer not found with ID: " + lawyerId));
            appointment.setLawyerId(lawyer);
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDto(savedAppointment);
    }
}
