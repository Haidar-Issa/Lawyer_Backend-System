package com.web.lawyer_backend_system.service.OpposingParty;

import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyFilterDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyRequestDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyResponseDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyUpdateRequestDto;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.OpposingParity;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.opposingParty.OpposingPartyMapper;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.OpposingPartyRepository;
import com.web.lawyer_backend_system.repository.specification.OpposingPartySpecifications;
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
public class OpposingPartyServiceImpl implements OpposingPartyService {
    private final OpposingPartyRepository opposingPartyRepository;
    private final CaseRepository caseRepository;
    private final OpposingPartyMapper opposingPartyMapper;

    @Override
    public OpposingPartyResponseDto createOpposingParty(OpposingPartyRequestDto dto) {
        Case_ legalCase = caseRepository.findById(dto.getCaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + dto.getCaseId()));

        OpposingParity party = opposingPartyMapper.toEntity(dto);
        party.setCaseId(legalCase);

        OpposingParity savedParty = opposingPartyRepository.save(party);
        return opposingPartyMapper.toResponseDto(savedParty);
    }

    @Override
    @Transactional(readOnly = true)
    public OpposingPartyResponseDto getOpposingPartyById(String id) {
        OpposingParity party = findById(id);
        return opposingPartyMapper.toResponseDto(party);
    }

    @Override
    public OpposingPartyResponseDto updateOpposingParty(String id, OpposingPartyUpdateRequestDto dto) {
        OpposingParity existingParty = findById(id);

        opposingPartyMapper.updateEntityFromDto(dto, existingParty);

        if (StringUtils.hasText(dto.getCaseId())) {
            Case_ legalCase = caseRepository.findById(dto.getCaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Case not found with ID: " + dto.getCaseId()));
            existingParty.setCaseId(legalCase);
        }

        OpposingParity updatedParty = opposingPartyRepository.save(existingParty);
        return opposingPartyMapper.toResponseDto(updatedParty);
    }

    @Override
    public void deleteOpposingParty(String id) {
        OpposingParity party = findById(id);
        opposingPartyRepository.delete(party);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OpposingPartyResponseDto> filterOpposingParties(OpposingPartyFilterDto filterDto, Pageable pageable) {
        Specification<OpposingParity> spec = OpposingPartySpecifications.build(filterDto);
        return opposingPartyRepository.findAll(spec, pageable).map(opposingPartyMapper::toResponseDto);
    }

    private OpposingParity findById(String id) {
        return opposingPartyRepository.findByOpposingPartyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Opposing party not found with ID: " + id));
    }
}
