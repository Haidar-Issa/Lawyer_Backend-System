package com.web.lawyer_backend_system.service.caseNote;

import com.web.lawyer_backend_system.dto.caseNote.CaseNoteFilterDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteRequestDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteResponseDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteUpdateDto;
import com.web.lawyer_backend_system.dto.caseNoteHistory.CaseNoteHistoryResponseDto;
import com.web.lawyer_backend_system.entity.CaseNote;
import com.web.lawyer_backend_system.entity.CaseNoteHistory;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.caseNote.CaseNoteMapper;
import com.web.lawyer_backend_system.mapper.caseNoteHistory.CaseNoteHistoryMapper;
import com.web.lawyer_backend_system.repository.CaseNoteRepository;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.UserRepository;
import com.web.lawyer_backend_system.repository.specification.CaseNoteSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CaseNoteServiceImpl implements CaseNoteService {

    private final CaseNoteRepository caseNoteRepository;
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;
    private final CaseNoteMapper caseNoteMapper;
    private final CaseNoteHistoryMapper caseNoteHistoryMapper;

    @Override
    public CaseNoteResponseDto createNote(CaseNoteRequestDto dto) {
        Case_ legalCase = caseRepository.findById(dto.getCaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Legal case not found with ID: " + dto.getCaseId()));

//        User currentUser = getCurrentAuthenticatedUser();

        User currentUser = userRepository.findByUserId(dto.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException("User not found with ID: " + dto.getUserId()));
        CaseNote caseNote = caseNoteMapper.toEntity(dto);
        caseNote.setLegalCase(legalCase);
        caseNote.setCreator(currentUser);

        CaseNote savedNote = caseNoteRepository.save(caseNote);
        return caseNoteMapper.toResponseDto(savedNote);
    }

    @Override
    @Transactional(readOnly = true)
    public CaseNoteResponseDto getNoteById(String noteId) {
        CaseNote caseNote = findCaseNoteById(noteId);
        return caseNoteMapper.toResponseDto(caseNote);
    }

    @Override
    public CaseNoteResponseDto updateNote(String noteId, CaseNoteUpdateDto dto) {
        CaseNote existingNote = findCaseNoteById(noteId);
        String updatedText = dto.getText();

        String previousText = existingNote.getText();

        if (!previousText.equals(dto.getText())) {
//            User currentUser = getCurrentAuthenticatedUser();
            User changeUser = userRepository.findByUserId(dto.getUserId()).orElseThrow(()
                    -> new ResourceNotFoundException("User not found with ID: " + dto.getUserId()));
            CaseNoteHistory historyRecord = new CaseNoteHistory();
            historyRecord.setCaseNote(existingNote);
            historyRecord.setOldText(previousText);
            historyRecord.setChangedBy(changeUser);
            historyRecord.setChangedAt(LocalDateTime.now());

            existingNote.getHistory().add(historyRecord);
            existingNote.setText(updatedText);

            caseNoteMapper.updateEntityFromDto(dto, existingNote);
            existingNote = caseNoteRepository.save(existingNote);
        }

        return caseNoteMapper.toResponseDto(existingNote);
    }

    @Override
    public void deleteNote(String noteId) {
        CaseNote caseNote = findCaseNoteById(noteId);
        caseNoteRepository.delete(caseNote);
    }

    @Override
    public Page<CaseNoteResponseDto> filterNotes(CaseNoteFilterDto filterDto, Pageable pageable) {
        Specification<CaseNote> spec = CaseNoteSpecifications.build(filterDto);
        Page<CaseNote> caseNotesPage = caseNoteRepository.findAll(spec, pageable);

        if(caseNotesPage.getTotalElements() == 0) {
            throw new ResourceNotFoundException("No case notes found");
        }

        return caseNotesPage.map(caseNoteMapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CaseNoteHistoryResponseDto> getNoteHistory(String noteId) {
        CaseNote caseNote = findCaseNoteById(noteId);
        return caseNote.getHistory().stream()
                .map(caseNoteHistoryMapper::toHistoryResponseDto)
                .collect(Collectors.toList());
    }


    private CaseNote findCaseNoteById(String noteId) {
        return caseNoteRepository.findCaseNoteByCaseNoteId(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Case note not found with ID: " + noteId));
    }

//    private User getCurrentAuthenticatedUser() {
//        String currentUserId = SecurityUtils.getCurrentUserId();
//        return userRepository.findById(currentUserId)
//                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found with ID: " + currentUserId));
//    }
}
