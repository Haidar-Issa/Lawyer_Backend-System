package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.cases.CaseFilterDto;
import com.web.lawyer_backend_system.entity.Case_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.web.lawyer_backend_system.enums.CaseStatus;
import jakarta.persistence.criteria.JoinType;


import java.time.LocalDateTime;

public class CaseSpecifications {

    private CaseSpecifications() {

    }

    // --- Master Builder Strategy ---
    public static Specification<Case_> build(CaseFilterDto criteria) {
        if (criteria == null) {
            return Specification.where(fetchRelations());
        }

        return Specification.where(fetchRelations())
                .and(hasKeyword(criteria.getSearchKeyword()))
                .and(hasStatus(criteria.getCaseStatus()))
                .and(hasCourtName(criteria.getCourtName()))
                .and(hasCaseNumber(criteria.getCaseNumber()))
                .and(hasAssignedLawyer(criteria.getAssignedLawyerId()))
                .and(hasCreator(criteria.getCreatorId()))
                .and(hasClient(criteria.getClientId()))
                .and(startDateBetween(criteria.getStartDateFrom(), criteria.getStartDateTo()));
    }

    // --- Performance & Fetch Joins (solve (N+1) problem) ---
    public static Specification<Case_> fetchRelations() {
        return (root, query, cb) -> {
            if (Long.class != query.getResultType() && countIt(query.getResultType())) {
                root.fetch("creator", JoinType.LEFT);
                root.fetch("assignedLawyer", JoinType.LEFT);
                root.fetch("client", JoinType.LEFT);

                query.distinct(true);
            }
            return cb.conjunction();
        };
    }

    private static boolean countIt(Class<?> resultType) {
        return resultType != null && !resultType.equals(Long.class) && !resultType.equals(long.class);
    }

    // --- Atomic Specifications ---

    public static Specification<Case_> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(keyword)) return null;
            String pattern = "%" + keyword.toLowerCase().trim() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Case_> hasStatus(CaseStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("caseStatus"), status);
    }

    public static Specification<Case_> hasCourtName(String courtName) {
        return (root, query, cb) -> !StringUtils.hasText(courtName) ? null :
                cb.like(cb.lower(root.get("courtName")), "%" + courtName.toLowerCase().trim() + "%");
    }

    public static Specification<Case_> hasCaseNumber(String caseNumber) {
        return (root, query, cb) -> caseNumber == null ? null : cb.equal(root.get("caseNumber"), caseNumber);
    }

    public static Specification<Case_> hasAssignedLawyer(String lawyerId) {
        return (root, query, cb) -> !StringUtils.hasText(lawyerId) ? null :
                cb.equal(root.get("assignedLawyer").get("userId"), lawyerId);
    }

    public static Specification<Case_> hasCreator(String creatorId) {
        return (root, query, cb) -> !StringUtils.hasText(creatorId) ? null :
                cb.equal(root.get("creator").get("userId"), creatorId);
    }

    public static Specification<Case_> hasClient(String clientId) {
        return (root, query, cb) -> !StringUtils.hasText(clientId) ? null :
                cb.equal(root.get("client").get("clientId"), clientId);
    }

    public static Specification<Case_> startDateBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from != null && to != null) {
                return cb.between(root.get("startDate"), from, to);
            } else if (from != null) {
                return cb.greaterThanOrEqualTo(root.get("startDate"), from);
            } else if (to != null) {
                return cb.lessThanOrEqualTo(root.get("startDate"), to);
            }
            return null;
        };
    }
}
