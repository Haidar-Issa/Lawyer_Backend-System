package com.web.lawyer_backend_system.entity;


import com.web.lawyer_backend_system.enums.CaseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cases")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Case_ {
    @Id
    @Column(name = "case_id")
    private String caseId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "case_status")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CaseStatus caseStatus = CaseStatus.NEW;

    @Column(nullable = false, name = "court_name")
    private String courtName;

    @Column(nullable = false, name = "case_number")
    @NotBlank(message = "Case Number is required")
    private String caseNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "assigned_lawyer_id")
    private User assignedLawyer;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "legalCase")
    @Builder.Default
    private List<CaseNote> caseNotes = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    @Builder.Default
    private List<OpposingParity> opposingParties = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "legalCase", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourtSession> courtSessions = new ArrayList<>();

    //    Time
    @Column(nullable = false, name = "start_date")
    @Builder.Default
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.caseId == null) {
            this.caseId = UUID.randomUUID().toString();
        }

        if (this.caseNumber == null) {
            this.caseNumber = "CASE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

}
