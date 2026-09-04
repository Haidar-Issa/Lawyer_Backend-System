package com.web.lawyer_backend_system.dto.task;
import com.web.lawyer_backend_system.enums.Priority;
import com.web.lawyer_backend_system.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskFilterDto {

    private String taskId;

    private String searchTitle;
    private Priority priority;
    private TaskStatus status;
    private String assignedLawyerId;
    private String caseId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDateTo;
}
