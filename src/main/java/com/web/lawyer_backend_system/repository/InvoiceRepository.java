package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.enums.InvoiceStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String>, JpaSpecificationExecutor<Invoice> {
    Optional<List<Invoice>> findByClient(Client client);

    Page<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<Invoice> findByInvoiceId(String invoiceId);

    Page<Invoice> findByIssueDateAndDueDate(LocalDate issueDate, LocalDate dueDate, Pageable pageable);

    Page<Invoice> findByPaidDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    Page<Invoice> findByPaidDateBefore(LocalDate date, Pageable pageable);

    Page<Invoice> findByPaidDateAfter(LocalDate date, Pageable pageable);

    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);

    Page<Invoice> findByCaseId(Case_ caseId, Pageable pageable);

    void deleteByInvoiceId(String invoiceId);

    @Query("""
                SELECT i FROM Invoice i
                WHERE i.client.clientId = :clientId
                ORDER BY i.paidDate DESC
            """)
    Optional<List<Invoice>> findLatestInvoicesByClientId(@Param("clientId") String clientId, Pageable pageable);
}
