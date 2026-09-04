package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.Case_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case_, String> {

    Optional<Case_> findByCaseId(String caseId);

    Page<Case_> findAll(Specification<Case_> spec, Pageable pageable);
}
