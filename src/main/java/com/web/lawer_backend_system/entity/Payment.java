package com.web.lawer_backend_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Column(nullable = false , name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, name = "payment_method")
    private String paymentMethod;

    @Column(nullable = false, name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name= "note")
    private String note;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @PrePersist
    public void prePersist() {
        if(this.paymentId == null) {
            this.paymentId = UUID.randomUUID().toString();
        }
    }
}
