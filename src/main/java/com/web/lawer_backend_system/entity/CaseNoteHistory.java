package com.web.lawer_backend_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "case_note_history")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseNoteHistory {
    @Id
    @Column(name = "history_id")
    private String historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_note_id", nullable = false)
    private CaseNote caseNote;

    @Column(name = "old_text", columnDefinition = "TEXT")
    private String oldText;

    @Column(name = "new_text", columnDefinition = "TEXT")
    private String newText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @CreationTimestamp
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

}
