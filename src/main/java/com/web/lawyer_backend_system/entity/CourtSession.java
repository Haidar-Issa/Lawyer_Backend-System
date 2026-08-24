package com.web.lawer_backend_system.entity;

import com.web.lawer_backend_system.enums.CourtSessionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "court_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtSession {

    @Id
    @Column(name = "court_session_id")
    private String courtSessionId;

    @Column(name = "session_date")
    private LocalDate sessionDate;

    @Column(name = "session_time")
    private LocalTime sessionTime;

    @Column(name = "court_room")
    private String courtRoom;

    @Column(name = "judge_name")
    private String judgeName;

    private String decision;

    @Column(name = "decision_date")
    private LocalDate decisionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CourtSessionStatus status = CourtSessionStatus.SCHEDULED;

    @ElementCollection
    @CollectionTable(
            name = "court_session_notes",
            joinColumns = @JoinColumn(name = "court_session_id")
    )
    @Column(name = "note", length = 1000)
    @Builder.Default
    private List<String> notes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.courtSessionId == null) {
            this.courtSessionId = UUID.randomUUID().toString();
        }
    }
}