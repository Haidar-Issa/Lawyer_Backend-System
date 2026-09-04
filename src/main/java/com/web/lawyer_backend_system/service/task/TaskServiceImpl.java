package com.web.lawyer_backend_system.service.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.web.lawyer_backend_system.dto.task.TaskFilterDto;
import com.web.lawyer_backend_system.dto.task.TaskRequestDto;
import com.web.lawyer_backend_system.dto.task.TaskResponseDto;
import com.web.lawyer_backend_system.dto.task.TaskUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.Task;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.enums.TaskStatus;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.task.TaskMapper;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.TaskRepository;
import com.web.lawyer_backend_system.repository.UserRepository;
import com.web.lawyer_backend_system.repository.specification.TaskSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CaseRepository caseRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDto createTask(TaskRequestDto dto) {
        Task task = taskMapper.toEntity(dto);

        check(task, dto.getAssignedLawyerId(), dto.getCaseId());

        if (task.getStatus() == TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDate.now());
        }

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponseDto(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(String taskId) {
        Task task = findTaskById(taskId);
        return taskMapper.toResponseDto(task);
    }

    @Override
    public TaskResponseDto updateTask(String taskId, TaskUpdateRequestDto dto) {
        Task existingTask = findTaskById(taskId);

        taskMapper.updateEntityFromDto(dto, existingTask);

        check(existingTask, dto.getAssignedLawyerId(), dto.getCaseId());

        if (existingTask.getStatus() == TaskStatus.COMPLETED && existingTask.getCompletedAt() == null) {
            existingTask.setCompletedAt(LocalDate.now());
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toResponseDto(updatedTask);
    }

    @Override
    public void deleteTask(String taskId) {
        Task task = findTaskById(taskId);
        taskRepository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponseDto> filterTasks(TaskFilterDto filterDto, Pageable pageable) {
        Specification<Task> spec = TaskSpecifications.build(filterDto);
        return taskRepository.findAll(spec, pageable).map(taskMapper::toResponseDto);
    }

    private Task findTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
    }

    private void check(Task existingTask, String assignedLawyerId, String caseId) {
        if (StringUtils.hasText(assignedLawyerId)) {
            User lawyer = userRepository.findById(assignedLawyerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Lawyer not found with ID: " + assignedLawyerId));
            existingTask.setAssignedLawyer(lawyer);
        }

        if (StringUtils.hasText(caseId)) {
            Case_ legalCase = caseRepository.findById(caseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + caseId));
            existingTask.setCaseId(legalCase);
        }
    }
}
