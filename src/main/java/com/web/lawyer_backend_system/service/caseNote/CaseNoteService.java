package com.web.lawyer_backend_system.service.caseNote;

import com.web.lawyer_backend_system.dto.caseNote.CaseNoteFilterDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteRequestDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteResponseDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteUpdateDto;
import com.web.lawyer_backend_system.dto.caseNoteHistory.CaseNoteHistoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CaseNoteService {
    CaseNoteResponseDto createNote(CaseNoteRequestDto dto);

    CaseNoteResponseDto getNoteById(String noteId);

    CaseNoteResponseDto updateNote(String noteId, CaseNoteUpdateDto dto);

    void deleteNote(String noteId);

    Page<CaseNoteResponseDto> filterNotes(CaseNoteFilterDto filterDto, Pageable pageable);

    List<CaseNoteHistoryResponseDto> getNoteHistory(String noteId);
}
