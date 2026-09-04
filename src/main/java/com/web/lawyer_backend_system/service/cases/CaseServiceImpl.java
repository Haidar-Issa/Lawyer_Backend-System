package com.web.lawyer_backend_system.service.cases;

import com.web.lawyer_backend_system.dto.cases.CaseFilterDto;
import com.web.lawyer_backend_system.dto.cases.CaseRequestDto;
import com.web.lawyer_backend_system.dto.cases.CaseResponseDto;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.cases.CaseMapper;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.ClientRepository;
import com.web.lawyer_backend_system.repository.UserRepository;
import com.web.lawyer_backend_system.repository.specification.CaseSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CaseServiceImpl implements CaseService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;

    @Override
    public CaseResponseDto createCase(CaseRequestDto requestDto) {
        validateEntitiesExist(requestDto.getCreator(), requestDto.getAssignedLawyer(), requestDto.getClient());

        Case_ case_ = caseMapper.toCaseEntity(requestDto);

        return caseMapper.toCaseResponseDto(caseRepository.save(case_));
    }

    @Override
    public CaseResponseDto updateCase(String caseId, CaseRequestDto requestDto) {
        Case_ case_ = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("case"));

             caseMapper.updateFromDto(requestDto,case_);

             return caseMapper.toCaseResponseDto(caseRepository.save(case_));

    }


    @Override
    public CaseResponseDto getCaseById(String caseId) {
       Case_ case_ = caseRepository.findById(caseId)
               .orElseThrow(()-> new ResourceNotFoundException("case is not found with Id: " + caseId));
       return caseMapper.toCaseResponseDto(case_);
    }


    @Override
    public void deleteCase(String caseId) {
            if(!caseRepository.existsById(caseId)) {
                throw new ResourceNotFoundException("case is not found with Id: " + caseId);
            }
            caseRepository.deleteById(caseId);
            log.info("Case is deleted successfully");
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CaseResponseDto> searchCases(CaseFilterDto criteria, Pageable pageable) {
        Specification<Case_> spec = CaseSpecifications.build(criteria);
        return caseRepository.findAll(spec, pageable)
                .map(caseMapper::toCaseResponseDto);
    }

    private void validateEntitiesExist(String creatorId, String lawyerId, String clientId) {
        if (creatorId != null && !userRepository.existsById(creatorId)) {
            throw new ResourceNotFoundException("Creator User not found with id: " + creatorId);
        }
        if (lawyerId != null && !userRepository.existsById(lawyerId)) {
            throw new ResourceNotFoundException("Assigned Lawyer User not found with id: " + lawyerId);
        }
        if (clientId != null && !clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client not found with id: " + clientId);
        }
    }

}
