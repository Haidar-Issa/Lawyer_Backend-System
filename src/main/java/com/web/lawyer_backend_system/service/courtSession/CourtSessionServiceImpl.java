package com.web.lawyer_backend_system.service.courtSession;

import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionFilterDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionRequestDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionResponseDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.CourtSession;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.courtSession.CourtSessionMapper;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.CourtSessionRepository;
import com.web.lawyer_backend_system.repository.specification.CourtSessionSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CourtSessionServiceImpl implements CourtSessionService {
    private final CourtSessionRepository courtSessionRepository;
    private final CaseRepository caseRepository;
    private final CourtSessionMapper courtSessionMapper;

    @Override
    public CourtSessionResponseDto createCourtSession(CourtSessionRequestDto dto) {
        Case_ legalCase = caseRepository.findById(dto.getCaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + dto.getCaseId()));

        CourtSession courtSession = courtSessionMapper.toEntity(dto);
        courtSession.setLegalCase(legalCase);

        CourtSession savedSession = courtSessionRepository.save(courtSession);
        return courtSessionMapper.toResponseDto(savedSession);
    }

    @Override
    @Transactional(readOnly = true)
    public CourtSessionResponseDto getCourtSessionById(String id) {
        CourtSession courtSession = findById(id);
        return courtSessionMapper.toResponseDto(courtSession);
    }

    @Override
    public CourtSessionResponseDto updateCourtSession(String id, CourtSessionUpdateRequestDto dto) {
        CourtSession existingSession = findById(id);

        courtSessionMapper.updateEntityFromDto(dto, existingSession);

        if (StringUtils.hasText(dto.getCaseId())) {
            Case_ legalCase = caseRepository.findById(dto.getCaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + dto.getCaseId()));
            existingSession.setLegalCase(legalCase);
        }

        CourtSession updatedSession = courtSessionRepository.save(existingSession);
        return courtSessionMapper.toResponseDto(updatedSession);
    }

    @Override
    public void deleteCourtSession(String id) {
        CourtSession courtSession = findById(id);
        courtSessionRepository.delete(courtSession);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourtSessionResponseDto> filterCourtSessions(CourtSessionFilterDto filterDto, Pageable pageable) {
        Specification<CourtSession> spec = CourtSessionSpecifications.build(filterDto);
        return courtSessionRepository.findAll(spec, pageable).map(courtSessionMapper::toResponseDto);
    }

    private CourtSession findById(String id) {
        return courtSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Court session not found with ID: " + id));
    }
}
