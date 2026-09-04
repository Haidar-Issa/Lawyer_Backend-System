package com.web.lawyer_backend_system.controller.cases;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.cases.CaseFilterDto;
import com.web.lawyer_backend_system.dto.cases.CaseRequestDto;
import com.web.lawyer_backend_system.dto.cases.CaseResponseDto;
import com.web.lawyer_backend_system.service.cases.CaseService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService caseService;

    @PostMapping
    public ResponseEntity<ApiResponse<CaseResponseDto>> createCase(@Valid @RequestBody CaseRequestDto caseRequestDto,
                                                                   HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "case is created successfully",
                        caseService.createCase(caseRequestDto),
                        request.getRequestURI()
                )
        );
    }

    @PatchMapping("/{caseId}")
    public ResponseEntity<ApiResponse<CaseResponseDto>> updateCase(@PathVariable String caseId,
                                                                   @Valid @RequestBody CaseRequestDto caseRequestDto,
                                                                   HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case is updated successfully",
                        caseService.updateCase(caseId, caseRequestDto),
                        request.getRequestURI()
                )
        );
    }

    @DeleteMapping("/{caseId}")
    public ResponseEntity<ApiResponse<Void>> deleteCase(@PathVariable String caseId,
                                                        HttpServletRequest request) {
        caseService.deleteCase(caseId);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case is deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<ApiResponse<CaseResponseDto>> getCaseById(@PathVariable String caseId,
                                                                    HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Case is retrieved successfully",
                        caseService.getCaseById(caseId),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<CaseResponseDto>>> search(@ModelAttribute CaseFilterDto caseFilterDto,
                                                                     HttpServletRequest request,
                                                                     @PageableDefault(size = 8, direction = Sort.Direction.DESC, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Cases is retrieved successfully",
                        caseService.searchCases(caseFilterDto, pageable),
                        request.getRequestURI()
                )
        );
    }
}
