package com.web.lawyer_backend_system.repository.specification;

import com.web.lawyer_backend_system.dto.appointment.AppointmentFilterDto;
import com.web.lawyer_backend_system.entity.Appointment;
import com.web.lawyer_backend_system.enums.AppointmentStatus;
import com.web.lawyer_backend_system.enums.AppointmentType;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class AppointmentSpecifications {
    private AppointmentSpecifications() {
    }

    public static Specification<Appointment> build(AppointmentFilterDto appointmentFilterDto) {
        if (appointmentFilterDto.getClientId() == null) {
            return Specification.where(fetchRelations());
        }
        return Specification.where(fetchRelations())
                .and(hasCase(appointmentFilterDto.getClientId()))
                .and(hasTitle(appointmentFilterDto.getClientId()))
                .and(hasAppointmentDate(appointmentFilterDto.getStartDateFrom(),appointmentFilterDto.getStartDateTo()))
                .and(hasLawyer(appointmentFilterDto.getLawyerId()))
                .and(hasStatus(appointmentFilterDto.getStatus()))
                .and(hasType(appointmentFilterDto.getType()))
                .and(hasClient(appointmentFilterDto.getClientId()));

    }

    public static Specification<Appointment> fetchRelations() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("caseId", JoinType.LEFT);
                root.fetch("clientId", JoinType.LEFT);
                root.fetch("lawyerId", JoinType.LEFT);
                query.distinct(true);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Appointment> hasTitle(String searchText) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(searchText)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("title"), "%" + searchText + "%");
        };

    }

    public static Specification<Appointment> hasStatus(AppointmentStatus appointmentStatus) {
        return (root, query, criteriaBuilder) -> {
            if (appointmentStatus == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), appointmentStatus);
        };
    }

    public static Specification<Appointment> hasType(AppointmentType appointmentType) {
        return (root, query, criteriaBuilder) -> {
            if (appointmentType == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("type"), appointmentType);
        };
    }

    public static Specification<Appointment> hasCase(String caseId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(caseId)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("caseId").get("caseId"), caseId);
        };
    }

    public static Specification<Appointment> hasClient(String clientId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(clientId)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("clientId").get("clientId"), clientId);
        };
    }

    public static Specification<Appointment> hasLawyer(String lawyerId) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(lawyerId)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("lawyerId").get("lawyerId"), lawyerId);
        };
    }

    public static Specification<Appointment> hasAppointmentDate(LocalDateTime startDateFrom, LocalDateTime startDateTo) {
        return (root, query, criteriaBuilder) -> {
            if (startDateFrom != null && startDateTo != null) {
                return criteriaBuilder.between(root.get("startDate"), startDateFrom, startDateTo);
            }
            else if(startDateTo != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDateTo);
            }
            else if(startDateFrom != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDateFrom);
            }
            else{
                return null;
            }
        };
    }
}
