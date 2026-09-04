package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.document.DocumentFilterDto;
import com.web.lawyer_backend_system.entity.Document;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;


public final class DocumentSpecifications {

    private DocumentSpecifications() {
    }

    public static Specification<Document> build(DocumentFilterDto filter) {
        if(filter==null){
            return Specification.where(fetchRelations());
        }
        return Specification.where(hasCaseId(filter.getCaseId()))
                .and(hasTitle(filter.getSearchTitle()))
                .and(hasFileName(filter.getFileName()))
                .and(hasUploaderId(filter.getUploaderId()));
    }


    public static Specification<Document> fetchRelations() {
        return (root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("caseId", JoinType.LEFT);
                root.fetch("uploader", JoinType.LEFT);

                query.distinct(true);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Document> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(title)) return null;

            String pattern = "%" + title.trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);
        };
    }

    public static Specification<Document> hasFileName(String fileName) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(fileName)) return null;

            String pattern = "%" + fileName.trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fileName")), pattern);
        };
    }

    public static Specification<Document> hasCaseId(String caseId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(caseId)) return null;
            return criteriaBuilder.equal(root.get("caseId").get("caseId"), caseId.trim());
        };
    }

    public static Specification<Document> hasUploaderId(String uploaderId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(uploaderId)) return null;
            return criteriaBuilder.equal(root.get("uploader").get("userId"), uploaderId.trim());
        };
    }
}