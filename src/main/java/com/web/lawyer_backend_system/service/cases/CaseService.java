package com.web.lawyer_backend_system.service.cases;

import com.web.lawyer_backend_system.dto.cases.CaseFilterDto;
import com.web.lawyer_backend_system.dto.cases.CaseRequestDto;
import com.web.lawyer_backend_system.dto.cases.CaseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface CaseService {
    CaseResponseDto createCase(CaseRequestDto requestDto);
    CaseResponseDto updateCase(String caseId,CaseRequestDto requestDto);
    CaseResponseDto getCaseById(String caseId);
    void deleteCase(String caseId);

    @Transactional(readOnly = true)
    Page<CaseResponseDto> searchCases(CaseFilterDto criteria, Pageable pageable);
}
