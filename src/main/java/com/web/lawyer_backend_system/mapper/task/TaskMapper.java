package com.web.lawyer_backend_system.mapper.task;
import com.web.lawyer_backend_system.dto.task.TaskRequestDto;
import com.web.lawyer_backend_system.dto.task.TaskResponseDto;
import com.web.lawyer_backend_system.dto.task.TaskUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Task;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskMapper {
    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "assignedLawyer", ignore = true)
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task toEntity(TaskRequestDto dto);

    @Mapping(source = "assignedLawyer.userId", target = "assignedLawyerId")
    @Mapping(source = "assignedLawyer.fullName", target = "assignedLawyerName")
    @Mapping(source = "caseId.caseId", target = "caseId")
    @Mapping(source = "caseId.title", target = "caseTitle")
    TaskResponseDto toResponseDto(Task entity);

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "assignedLawyer", ignore = true)
    @Mapping(target = "caseId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(TaskUpdateRequestDto dto, @MappingTarget Task entity);
}
