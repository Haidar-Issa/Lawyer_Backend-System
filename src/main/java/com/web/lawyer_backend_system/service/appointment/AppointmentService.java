package com.web.lawyer_backend_system.service.appointment;

import com.web.lawyer_backend_system.dto.appointment.AppointmentFilterDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentRequestDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentResponseDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(AppointmentRequestDto dto);

    AppointmentResponseDto getAppointmentById(String id);

    AppointmentResponseDto updateAppointment(String id, AppointmentUpdateRequestDto dto);

    void deleteAppointment(String id);

    Page<AppointmentResponseDto> filterAppointments(AppointmentFilterDto filterDto, Pageable pageable);
}
