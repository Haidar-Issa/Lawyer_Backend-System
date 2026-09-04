package com.web.lawyer_backend_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "opposing_parities")
@Getter
@Setter
public class OpposingParity {
    @Id
    @Column(name = "opposing_parity_id")
    private String opposingPartyId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "lawyer_name")
    private String lawyerName;

    @Column(name = "lawyer_phone")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid lawyer phone number format")
    private String lawyerPhone;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    @NotBlank(message = "Case ID is required")
    private Case_ caseId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;;

    @PrePersist
    public void prePersist() {
        if(this.opposingPartyId == null){
            this.opposingPartyId = UUID.randomUUID().toString();
        }

    }

}
