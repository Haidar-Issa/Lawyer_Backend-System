package com.web.lawyer_backend_system.dto.appointment;
import com.web.lawyer_backend_system.enums.AppointmentStatus;
import com.web.lawyer_backend_system.enums.AppointmentType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class AppointmentFilterDto {
    private String searchTitle;
    private AppointmentStatus status;
    private AppointmentType type;
    private String caseId;
    private String clientId;
    private String lawyerId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTo;
}
