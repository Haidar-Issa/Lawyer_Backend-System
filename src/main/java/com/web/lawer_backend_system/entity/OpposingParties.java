package com.web.lawer_backend_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "opposing_parities")
public class OpposingParties {
    @Id
    @Column(name = "opposing_parity_id")
    private String opposingParityId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "lawyer_name")
    private String lawyerName;

    @Column(name = "lawyer_phone")
    private String lawyerPhone;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private Case caseId;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        if(this.opposingParityId == null){
            this.opposingParityId = UUID.randomUUID().toString();
        }

    }

}
