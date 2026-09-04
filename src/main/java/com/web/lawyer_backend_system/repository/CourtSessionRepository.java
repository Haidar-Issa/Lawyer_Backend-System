package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.CourtSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtSessionRepository extends JpaRepository<CourtSession, String>, JpaSpecificationExecutor<CourtSession> {
Page<CourtSession> findAll(Specification<CourtSession> spec, Pageable pageable);
}
