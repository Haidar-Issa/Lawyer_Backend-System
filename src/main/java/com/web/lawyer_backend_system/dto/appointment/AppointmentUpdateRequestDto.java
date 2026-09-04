package com.web.lawyer_backend_system.dto.appointment;
import com.web.lawyer_backend_system.enums.AppointmentStatus;
import com.web.lawyer_backend_system.enums.AppointmentType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentUpdateRequestDto {
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String location;

    private AppointmentStatus status;

    private AppointmentType type;

    private String caseId;

    private String clientId;

    private String lawyerId;
}
