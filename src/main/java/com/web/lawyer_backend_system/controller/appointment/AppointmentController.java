package com.web.lawyer_backend_system.controller.appointment;
import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.appointment.AppointmentFilterDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentRequestDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentResponseDto;
import com.web.lawyer_backend_system.dto.appointment.AppointmentUpdateRequestDto;
import com.web.lawyer_backend_system.service.appointment.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointment(
            @Valid @RequestBody AppointmentRequestDto requestDto,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Appointment scheduled successfully",
                        appointmentService.createAppointment(requestDto),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> getAppointmentById(
            @PathVariable String id,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Appointment retrieved successfully",
                        appointmentService.getAppointmentById(id),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> updateAppointment(
            @PathVariable String id,
            @Valid @RequestBody AppointmentUpdateRequestDto updateRequestDto,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Appointment updated successfully",
                        appointmentService.updateAppointment(id, updateRequestDto),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(
            @PathVariable String id,
            HttpServletRequest request) {

        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Appointment deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<AppointmentResponseDto>>> searchAppointments(
            @ModelAttribute AppointmentFilterDto filterDto,
            @PageableDefault(sort = "start_date", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Appointments retrieved successfully using filter criteria",
                        appointmentService.filterAppointments(filterDto, pageable),
                        request.getRequestURI()
                )
        );
    }
}
