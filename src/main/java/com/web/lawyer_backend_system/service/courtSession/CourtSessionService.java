package com.web.lawyer_backend_system.service.courtSession;

import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionFilterDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionRequestDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionResponseDto;
import com.web.lawyer_backend_system.dto.courtSessions.CourtSessionUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourtSessionService {CourtSessionResponseDto createCourtSession(CourtSessionRequestDto dto);

    CourtSessionResponseDto getCourtSessionById(String id);

    CourtSessionResponseDto updateCourtSession(String id, CourtSessionUpdateRequestDto dto);

    void deleteCourtSession(String id);

    Page<CourtSessionResponseDto> filterCourtSessions(CourtSessionFilterDto filterDto, Pageable pageable);

}
