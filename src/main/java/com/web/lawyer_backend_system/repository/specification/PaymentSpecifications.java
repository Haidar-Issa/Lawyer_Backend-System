package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.payment.PaymentFilterDto;
import com.web.lawyer_backend_system.entity.Payment;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentSpecifications {

    private PaymentSpecifications() {
    }

    public static Specification<Payment> build(PaymentFilterDto filterDto) {
        Specification<Payment> spec = Specification.where(fetchRelation());

        if (filterDto == null) {
            return spec;
        }

        return spec.and(hasInvoiceId(filterDto.getInvoiceId()))
                .and(hasPaymentMethod(filterDto.getPaymentMethod()))
                .and(amountBetween(filterDto.getMinAmount(), filterDto.getMaxAmount()))
                .and(dateBetween(filterDto.getStartDate(), filterDto.getEndDate()))
                .and(hasNote(filterDto.getNote()));
    }

    public static Specification<Payment> fetchRelation() {
        return (root, query, cb) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("invoice", JoinType.LEFT);
                query.distinct(true);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Payment> hasInvoiceId(String invoiceId) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(invoiceId) || invoiceId.trim().isEmpty()) {
                return null;
            }
            return cb.equal(root.get("invoice").get("invoiceId"), invoiceId.trim());
        };
    }

    public static Specification<Payment> hasPaymentMethod(String paymentMethod) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(paymentMethod) || paymentMethod.trim().isEmpty()) {
                return null;
            }
            return cb.equal(cb.lower(root.get("paymentMethod")), paymentMethod.trim().toLowerCase());
        };
    }

    public static Specification<Payment> amountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        return (root, query, cb) -> {
            if (minAmount != null && maxAmount != null) {
                return cb.between(root.get("amount"), minAmount, maxAmount);
            } else if (minAmount != null) {
                return cb.greaterThanOrEqualTo(root.get("amount"), minAmount);
            } else if (maxAmount != null) {
                return cb.lessThanOrEqualTo(root.get("amount"), maxAmount);
            }
            return null;
        };
    }

    public static Specification<Payment> dateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null) {
                return cb.between(root.get("paymentDate"), startDate, endDate);
            } else if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("paymentDate"), startDate);
            } else if (endDate != null) {
                return cb.lessThanOrEqualTo(root.get("paymentDate"), endDate);
            }
            return null;
        };
    }

    public static Specification<Payment> hasNote(String note) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(note) || note.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("note")), "%" + note.trim().toLowerCase() + "%");
        };
    }
}