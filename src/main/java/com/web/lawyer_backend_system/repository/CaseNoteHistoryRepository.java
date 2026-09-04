package com.web.lawyer_backend_system.repository;

import com.web.lawyer_backend_system.entity.CaseNote;
import com.web.lawyer_backend_system.entity.CaseNoteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseNoteHistoryRepository extends JpaRepository<CaseNoteHistory, String> {
    Optional<CaseNoteHistory> findByCaseNote(CaseNote caseNote);
    List<CaseNoteHistory> findByCaseNoteCaseNoteIdOrderByChangedAtDesc(String caseNoteId);
}
