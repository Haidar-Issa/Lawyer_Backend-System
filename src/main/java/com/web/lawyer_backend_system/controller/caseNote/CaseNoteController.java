package com.web.lawyer_backend_system.controller.caseNote;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteFilterDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteRequestDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteResponseDto;
import com.web.lawyer_backend_system.dto.caseNote.CaseNoteUpdateDto;
import com.web.lawyer_backend_system.dto.caseNoteHistory.CaseNoteHistoryResponseDto;
import com.web.lawyer_backend_system.service.caseNote.CaseNoteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/case-notes")
@RequiredArgsConstructor
public class CaseNoteController {

    private final CaseNoteService caseNoteService;

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<CaseNoteResponseDto>> createNote(
            @Valid @RequestBody CaseNoteRequestDto requestDto,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Case note created successfully",
                        caseNoteService.createNote(requestDto),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<ApiResponse<CaseNoteResponseDto>> getNoteById(
            @PathVariable String noteId,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case note retrieved successfully",
                        caseNoteService.getNoteById(noteId),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PutMapping("/{noteId}")
    public ResponseEntity<ApiResponse<CaseNoteResponseDto>> updateNote(
            @PathVariable String noteId,
            @Valid @RequestBody CaseNoteUpdateDto updateRequestDto,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case note updated successfully",
                        caseNoteService.updateNote(noteId, updateRequestDto),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/{noteId}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(
            @PathVariable String noteId,
            HttpServletRequest request) {

        caseNoteService.deleteNote(noteId);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case note deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<CaseNoteResponseDto>>> searchNotes(
            @ModelAttribute CaseNoteFilterDto filterDto,
            @PageableDefault( sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case notes retrieved successfully using filter criteria",
                        caseNoteService.filterNotes(filterDto, pageable),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{noteId}/history")
    public ResponseEntity<ApiResponse<List<CaseNoteHistoryResponseDto>>> getNoteHistory(
            @PathVariable String noteId,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case note history retrieved successfully",
                        caseNoteService.getNoteHistory(noteId),
                        request.getRequestURI()
                )
        );
    }
}
