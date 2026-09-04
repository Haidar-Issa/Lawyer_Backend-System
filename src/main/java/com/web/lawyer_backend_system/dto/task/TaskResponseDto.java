package com.web.lawyer_backend_system.dto.task;
import com.web.lawyer_backend_system.enums.Priority;
import com.web.lawyer_backend_system.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponseDto {
    private String taskId;
    private String title;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private LocalDate completedAt;

    private String assignedLawyerId;
    private String assignedLawyerName;

    private String caseId;
    private String caseTitle;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
