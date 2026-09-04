package com.web.lawyer_backend_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE clients SET is_deleted = true WHERE client_id = ?")
@SQLRestriction("is_deleted = false")
public class Client {
    @Id
    @Column(name = "client_id")
    private String clientId;

    @Column(nullable = false,name = "full_name")
    private String fullName;

    @Column(nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(nullable = false, name = "national_number", unique = true)
    private BigInteger nationalNumber;

    @Column(unique = true, name = "phone_number", nullable = false)
    private BigInteger phoneNumber;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "lawyer_id"
    )
    private User lawyer;

    @Column(nullable = false , name = "is_deleted")
    @Builder.Default
    private boolean isDelete = false;

//    Relation
    @OneToMany(mappedBy = "client" , fetch = FetchType.LAZY)
    @Builder.Default
    private List<Case_> aCases = new ArrayList<>();

    @OneToMany(mappedBy = "clientId", fetch = FetchType.LAZY)
    @Builder.Default

    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Invoice> invoices = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.clientId == null) {
            this.clientId = UUID.randomUUID().toString();
        }
    }

}
