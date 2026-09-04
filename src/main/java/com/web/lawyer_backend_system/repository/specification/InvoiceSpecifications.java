package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.enums.InvoiceStatus;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public final class InvoiceSpecifications {

    private InvoiceSpecifications() {}


    public static Specification<Invoice> fetchRelations() {
        return (root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("caseId", JoinType.LEFT);
                root.fetch("client", JoinType.LEFT);

                //not duplicated results when using fetch Join
                query.distinct(true);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Invoice> hasPaidBetween(LocalDate start, LocalDate end) {
        return (root, query, criteriaBuilder) -> {
            if (start == null || end == null || start.isAfter(end)) {
                return null;
            }
            return criteriaBuilder.between(root.get("paidDate"), start, end);
        };
    }


    public static Specification<Invoice> hasIssueDate(LocalDate issueDate) {
        return (root, query, criteriaBuilder) -> {
            if (issueDate == null) return null;
            return criteriaBuilder.equal(root.get("issueDate"), issueDate);
        };
    }

    public static Specification<Invoice> hasDueDate(LocalDate dueDate) {
        return (root, query, criteriaBuilder) -> {
            if (dueDate == null) return null;
            return criteriaBuilder.equal(root.get("dueDate"), dueDate);
        };
    }

    public static Specification<Invoice> hasInvoiceStatus(InvoiceStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Invoice> hasCaseId(String caseId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(caseId)) return null;
            return criteriaBuilder.equal(root.get("caseId").get("caseId"), caseId);
        };
    }

    public static Specification<Invoice> hasInvoiceId(String invoiceId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(invoiceId)) return null;
            return criteriaBuilder.equal(root.get("invoiceId"), invoiceId);
        };
    }

    public static Specification<Invoice> hasClientId(String clientId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(clientId)) return null;
            return criteriaBuilder.equal(root.get("client").get("clientId"), clientId);
        };
    }

    public static Specification<Invoice> hasNote(String keywords) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keywords)) return null;

            String pattern = "%" + keywords.trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("notes")), pattern);
        };
    }
}