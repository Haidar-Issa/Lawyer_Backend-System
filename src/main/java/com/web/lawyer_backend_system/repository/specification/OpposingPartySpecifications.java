package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyFilterDto;
import com.web.lawyer_backend_system.entity.OpposingParity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class OpposingPartySpecifications {


    public static Specification<OpposingParity> build(OpposingPartyFilterDto filterDto) {
        if (filterDto == null) {
            return Specification.where(fetchRelations());
        }
        return Specification.where(fetchRelations())
                .and(hasFullName(filterDto.getSearchName()))
                .and(fullNameContains(filterDto.getSearchName()))
                .and(hasPhoneNumber(filterDto.getPhoneNumber()))
                .and(hasEmail(filterDto.getEmail()))
                .and(hasLawyerName(filterDto.getLawyerName()))
                .and(hasCaseId(filterDto.getCaseId()));
    }

    public static Specification<OpposingParity> fetchRelations() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("legalCase", JoinType.LEFT);
                query.distinct(true);
            }

            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<OpposingParity> hasFullName(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(fullName)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%");
        };
    }

    public static Specification<OpposingParity> fullNameContains(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (fullName == null || fullName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%");
        };
    }

    public static Specification<OpposingParity> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(phoneNumber)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
        };
    }

    public static Specification<OpposingParity> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(email)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<OpposingParity> hasLawyerName(String lawyerName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(lawyerName)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("lawyerName")), "%" + lawyerName.toLowerCase() + "%");
        };
    }
    public static Specification<OpposingParity> hasCaseId(String caseId) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(caseId)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("legalCase").get("caseId"), caseId);
        };
    }
}
