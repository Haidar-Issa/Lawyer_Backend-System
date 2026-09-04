package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.CaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CaseNoteRepository extends JpaRepository<CaseNote, String>, JpaSpecificationExecutor<CaseNote> {
    Optional<CaseNote> findCaseNoteByCaseNoteId(String caseNoteId);

}
