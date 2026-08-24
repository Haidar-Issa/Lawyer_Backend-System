package com.web.lawer_backend_system.entity;

import com.web.lawer_backend_system.enums.CaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
public class Case {
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
    private int caseNumber;

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
    private List<CaseNote> caseNotes = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "caseId", fetch = FetchType.LAZY)
    private List<OpposingParties> opposingParties = new ArrayList<>();

//    Time
    @Column(nullable = false , name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist()
    {
        if(this.caseId == null){
            this.caseId = UUID.randomUUID().toString();
        }
    }

}
