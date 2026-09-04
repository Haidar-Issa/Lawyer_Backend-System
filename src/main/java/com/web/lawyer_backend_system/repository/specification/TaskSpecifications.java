package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.task.TaskFilterDto;
import com.web.lawyer_backend_system.entity.Task;
import com.web.lawyer_backend_system.enums.Priority;
import com.web.lawyer_backend_system.enums.TaskStatus;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class TaskSpecifications {

    public static Specification<Task> build(TaskFilterDto filterDto) {
        if (filterDto == null) {
            return null;
        }

        return Specification.where(
                hasTaskId(filterDto.getTaskId())
                        .and(hasCaseId(filterDto.getCaseId()))
                        .and(hasAssignedLawyerId(filterDto.getAssignedLawyerId()))
                        .and(hasStatus(filterDto.getStatus()))
                        .and(hasPriority(filterDto.getPriority()))
                        .and(hasDueDate(filterDto.getDueDateFrom(), filterDto.getDueDateTo()))
                        .and(hasTitle(filterDto.getSearchTitle()))
        );
    }

    public static Specification<Task> fetchRelations() {
        return (root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("legalCase", JoinType.LEFT);
                root.fetch("assignedTo", JoinType.LEFT);

                query.distinct(true);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Task> hasTaskId(String taskId) {
        return (root, query, criteriaBuilder) -> {
            if (taskId == null || taskId.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("taskId"), taskId);
        };
    }

    public static Specification<Task> hasCaseId(String caseId) {
        return (root, query, criteriaBuilder) -> {
            if (caseId == null || caseId.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("legalCase").get("caseId"), caseId);
        };
    }

    public static Specification<Task> hasAssignedLawyerId(String assignedLawyerId) {
        return (root, query, criteriaBuilder) -> {
            if (assignedLawyerId == null || assignedLawyerId.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("assignedTo").get("userId"), assignedLawyerId);
        };
    }

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.toString().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Task> hasPriority(Priority priority) {
        return (root, query, criteriaBuilder) -> {
            if (priority == null || priority.toString().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("priority"), priority);
        };
    }

    public static Specification<Task> hasDueDate(LocalDate dueDateFrom, LocalDate dueDateTo) {
        return (root, query, criteriaBuilder) -> {
            if (dueDateFrom == null && dueDateTo == null) {
                return criteriaBuilder.conjunction();
            } else if (dueDateFrom != null && dueDateTo != null) {
                return criteriaBuilder.between(root.get("dueDate"), dueDateFrom, dueDateTo);
            } else if (dueDateFrom != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dueDate"), dueDateFrom);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("dueDate"), dueDateTo);
            }
        };
    }

    public static Specification<Task> hasTitle(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(keyword) || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
        };
    }
}
