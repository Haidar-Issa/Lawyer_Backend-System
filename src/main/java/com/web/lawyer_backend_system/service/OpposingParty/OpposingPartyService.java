package com.web.lawyer_backend_system.service.OpposingParty;

import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyFilterDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyRequestDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyResponseDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OpposingPartyService {
    OpposingPartyResponseDto createOpposingParty(OpposingPartyRequestDto dto);

    OpposingPartyResponseDto getOpposingPartyById(String id);

    OpposingPartyResponseDto updateOpposingParty(String id, OpposingPartyUpdateRequestDto dto);

    void deleteOpposingParty(String id);

    Page<OpposingPartyResponseDto> filterOpposingParties(OpposingPartyFilterDto filterDto, Pageable pageable);
}
