package com.web.lawer_backend_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Client {
    @Id
    private String client_id;

    @Column(nullable = false)
    private String full_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private BigInteger national_number;

    @Column(unique = true)
    private BigInteger phone_number;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "lawyer_id"
    )
    private User lawyer;

//    Relation
    @OneToMany(mappedBy = "client" , fetch = FetchType.LAZY)
    private List<Case>  cases = new ArrayList<>();

    @OneToMany(mappedBy = "clientId", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        if (this.client_id == null) {
            this.client_id = UUID.randomUUID().toString();
        }
    }

}
