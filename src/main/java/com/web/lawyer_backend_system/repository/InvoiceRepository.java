package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.enums.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByClient(Client client);

    Page<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Invoice findByInvoiceId(String invoiceId);

    Invoice findByIssueDateAndDueDate(LocalDate issueDate, LocalDate dueDate);

    List<Invoice> findByPaidDateBetween(LocalDate start, LocalDate end);

    List<Invoice> findByPaidDateBefore(LocalDate date);

    List<Invoice> findByPaidDateAfter(LocalDate date);

    List<Invoice> findByStatus(InvoiceStatus status);


}
