package com.web.lawyer_backend_system.entity;


import com.web.lawyer_backend_system.enums.InvoiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@SQLDelete(sql = "UPDATE invoices SET is_delete = true WHERE id = ?")
@SQLRestriction("is_delete = false")
public class Invoice {
    @Id
    @Column(name = "invoice_id")
    private String invoiceId;

    @Column(name = "invoice_number", nullable = false)
    @NotBlank(message = "Invoice number is Required")
    private String invoiceNumber;

    @Column(name = "amount",nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @Column(name = "paid_amount")
    @DecimalMin(value = "0.0")
    @Builder.Default
    private BigDecimal paidAmount =  BigDecimal.ZERO;

    @Column(name = "remaining_amount")
    @DecimalMin(value = "0.0")
    @Builder.Default
    private BigDecimal remainingAmount =  BigDecimal.ZERO;

    @Column(name = "currency")
    @Builder.Default
    private String currency = "SYR";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private InvoiceStatus status = InvoiceStatus.PARTIALLY_PAID;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "paid_date")
    @Builder.Default
    private LocalDate paidDate = null;

    @Column(name = "notes" ,columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_delete")
    @Builder.Default
    private Boolean isDelete = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    @Builder.Default

    private List <Payment> payments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private Case_ caseId;

    @CreationTimestamp
    @Column(name = "created_at" ,updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if(this.invoiceId == null) {
            this.invoiceId = UUID.randomUUID().toString();
        }
        if(invoiceNumber == null) {
            this.invoiceNumber = "INV-"+System.currentTimeMillis();
        }
        if (this.amount != null && this.paidAmount != null) {
            this.remainingAmount = this.amount.subtract(this.paidAmount);
        }
    }

}
