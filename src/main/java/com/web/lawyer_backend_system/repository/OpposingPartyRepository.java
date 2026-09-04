package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.OpposingParity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpposingPartyRepository extends JpaRepository<OpposingParity, Long>, JpaSpecificationExecutor<OpposingParity> {
    Optional<OpposingParity> findByOpposingPartyId(String opposingPartyId);

    Page<OpposingParity> findAll(Specification<OpposingParity> spec, Pageable pageable);
}
