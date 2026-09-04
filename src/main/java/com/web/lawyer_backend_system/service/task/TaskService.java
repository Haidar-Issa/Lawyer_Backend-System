package com.web.lawyer_backend_system.service.task;

import com.web.lawyer_backend_system.dto.task.TaskFilterDto;
import com.web.lawyer_backend_system.dto.task.TaskRequestDto;
import com.web.lawyer_backend_system.dto.task.TaskResponseDto;
import com.web.lawyer_backend_system.dto.task.TaskUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {TaskResponseDto createTask(TaskRequestDto dto);

    TaskResponseDto getTaskById(String taskId);

    TaskResponseDto updateTask(String taskId, TaskUpdateRequestDto dto);

    void deleteTask(String taskId);

    Page<TaskResponseDto> filterTasks(TaskFilterDto filterDto, Pageable pageable);

}
