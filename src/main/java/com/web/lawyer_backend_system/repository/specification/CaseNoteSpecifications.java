package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.caseNote.CaseNoteFilterDto;
import com.web.lawyer_backend_system.entity.CaseNote;
import org.springframework.data.jpa.domain.Specification;

public class CaseNoteSpecifications {

    public static Specification<CaseNote> build(CaseNoteFilterDto filterDto) {

        if (filterDto == null) {
            return null;
        }

        return Specification.where(
                searchByCaseNoteName(filterDto.getSearchText())
                        .and(searchByCaseId(filterDto.getCaseId()))
                        .and(searchByCreatedById(filterDto.getCreatedById()))
                        .and(searchByCaseNoteId(filterDto.getCaseNoteId()))

        );

    }


    public static Specification<CaseNote> searchByCaseNoteName(String caseNoteName) {
        return (root, query, criteriaBuilder) -> {
            if (caseNoteName == null || caseNoteName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("text")), "%" + caseNoteName.trim().toLowerCase() + "%");
        };
    }

    public static Specification<CaseNote> searchByCaseId(String caseId) {
        return (root, query, criteriaBuilder) -> {
            if (caseId == null || caseId.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("legalCase").get("caseId"), caseId);
        };
    }

    public static Specification<CaseNote> searchByCreatedById(String createdById) {
        return (root, query, criteriaBuilder) -> {
            if (createdById == null || createdById.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("creator").get("userId"), createdById);
        };
    }

    public static Specification<CaseNote> searchByCaseNoteId(String caseNoteId) {
        return (root, query, criteriaBuilder) -> {
            if (caseNoteId == null || caseNoteId.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("caseNoteId"), caseNoteId);
        };
    }
}
