package com.web.lawyer_backend_system.dto.appointment;
import com.web.lawyer_backend_system.enums.AppointmentStatus;
import com.web.lawyer_backend_system.enums.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDto {
    private String appointmentId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private AppointmentStatus status;
    private AppointmentType type;

    private String caseId;
    private String caseTitle;

    private String clientId;
    private String clientName;

    private String lawyerId;
    private String lawyerName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
