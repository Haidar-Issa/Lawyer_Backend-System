package com.web.lawyer_backend_system.dto.task;
import com.web.lawyer_backend_system.enums.Priority;
import com.web.lawyer_backend_system.enums.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskUpdateRequestDto {
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String description;

    private Priority priority;

    private TaskStatus status;

    private LocalDate dueDate;

    private LocalDate completedAt;

    private String assignedLawyerId;

    private String caseId;
}
