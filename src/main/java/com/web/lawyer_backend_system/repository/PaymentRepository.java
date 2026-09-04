package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment> {
    Optional<Payment> findByPaymentId(String paymentId);
    Page<Payment> findByInvoice(Invoice invoice, Pageable pageable);
    Page<Payment> findAll(Specification<Payment> spec, Pageable pageable);
}
