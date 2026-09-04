package com.web.lawyer_backend_system.controller.task;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.task.TaskFilterDto;
import com.web.lawyer_backend_system.dto.task.TaskRequestDto;
import com.web.lawyer_backend_system.dto.task.TaskResponseDto;
import com.web.lawyer_backend_system.dto.task.TaskUpdateRequestDto;
import com.web.lawyer_backend_system.service.task.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseDto>> createTask(
            @Valid @RequestBody TaskRequestDto requestDto,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Task created successfully",
                        taskService.createTask(requestDto),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDto>> getTaskById(
            @PathVariable String taskId,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Task retrieved successfully",
                        taskService.getTaskById(taskId),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDto>> updateTask(
            @PathVariable String taskId,
            @Valid @RequestBody TaskUpdateRequestDto updateRequestDto,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Task updated successfully",
                        taskService.updateTask(taskId, updateRequestDto),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
            @PathVariable String taskId,
            HttpServletRequest request) {

        taskService.deleteTask(taskId);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Task deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<TaskResponseDto>>> searchTasks(
            @ModelAttribute TaskFilterDto filterDto,
            @PageableDefault( sort = "dueDate", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Tasks retrieved successfully using filter criteria",
                        taskService.filterTasks(filterDto, pageable),
                        request.getRequestURI()
                )
        );
    }
}
