package com.web.lawyer_backend_system.entity;

import com.web.lawyer_backend_system.enums.Priority;
import com.web.lawyer_backend_system.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@SQLDelete(sql = "UPDATE tasks SET is_deleted = true WHERE task_id = ?")
@SQLRestriction("is_deleted = false")
public class Task {
    @Id
    @Column(name = "task_id")
    private String taskId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    @Builder.Default
    private Priority priority = Priority.LOW;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private TaskStatus status = TaskStatus.TODO;

    @Column(nullable = false, name = "due_date")
    private LocalDate dueDate;

    @Column(nullable = false, name = "completed_at")
    private LocalDate completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_id")
    private User assignedLawyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private Case_ caseId;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if(this.taskId == null) {
            this.taskId = UUID.randomUUID().toString();
        }
    }

}
