package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionFilterDto;
import com.web.lawyer_backend_system.entity.CourtSession;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;


public class CourtSessionSpecifications {
    private CourtSessionSpecifications() {
    }

    public static Specification<CourtSession> build(CourtSessionFilterDto filterDto) {
        if (filterDto == null) {
            return Specification.where(fetchRelations());
        }

        return Specification.where(fetchRelations())
                .and(hasCaseId(filterDto.getCaseId()))
                .and(hasCourtRoom(filterDto.getCourtRoom()))
                .and(hasJudgeName(filterDto.getSearchJudgeName()))
                .and(hasStatus(filterDto.getStatus()))
                .and(hasSessionDate(filterDto.getSessionDateFrom(), filterDto.getSessionDateTo()));

    }

    public static Specification<CourtSession> fetchRelations() {
        return (root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("legalCase", JoinType.LEFT);

                query.distinct(true);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<CourtSession> hasJudgeName(String judgeName) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(judgeName)) return null;

            String pattern = "%" + judgeName.trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("judgeName")), pattern);
        };
    }

    public static Specification<CourtSession> hasCourtRoom(String courtRoom) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(courtRoom)) return null;
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("courtRoom")), courtRoom.trim().toLowerCase());
        };
    }

    public static Specification<CourtSession> hasStatus(Object status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<CourtSession> hasCaseId(String caseId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(caseId)) return null;
            return criteriaBuilder.equal(root.get("legalCase").get("caseId"), caseId.trim());
        };
    }

    public static Specification<CourtSession> hasSessionDate(LocalDate start, LocalDate end) {
        return (root, query, criteriaBuilder) -> {
            if (start != null && end != null) {
                return criteriaBuilder.between(root.get("sessionDate"), start, end);
            } else if (start != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("sessionDate"), start);
            } else if (end != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("sessionDate"), end);
            } else {
                return null;
            }
        };
    }

}
