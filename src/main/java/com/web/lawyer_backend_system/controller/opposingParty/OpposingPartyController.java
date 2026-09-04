package com.web.lawyer_backend_system.controller.opposingParty;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyFilterDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyRequestDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyResponseDto;
import com.web.lawyer_backend_system.dto.opposingParity.OpposingPartyUpdateRequestDto;
import com.web.lawyer_backend_system.service.OpposingParty.OpposingPartyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/opposing-parties")
public class OpposingPartyController {
    private final OpposingPartyService opposingPartyService;

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<OpposingPartyResponseDto>> createOpposingParty(
            @Valid @RequestBody OpposingPartyRequestDto requestDto,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Opposing party created successfully",
                        opposingPartyService.createOpposingParty(requestDto),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OpposingPartyResponseDto>> getOpposingPartyById(
            @PathVariable String id,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Opposing party retrieved successfully",
                        opposingPartyService.getOpposingPartyById(id),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OpposingPartyResponseDto>> updateOpposingParty(
            @PathVariable String id,
            @Valid @RequestBody OpposingPartyUpdateRequestDto updateRequestDto,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Opposing party updated successfully",
                        opposingPartyService.updateOpposingParty(id, updateRequestDto),
                        request.getRequestURI()
                )
        );
    }

    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOpposingParty(
            @PathVariable String id,
            HttpServletRequest request) {

        opposingPartyService.deleteOpposingParty(id);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Opposing party deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<OpposingPartyResponseDto>>> searchOpposingParties(
            @ModelAttribute OpposingPartyFilterDto filterDto,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Opposing parties retrieved successfully using filter criteria",
                        opposingPartyService.filterOpposingParties(filterDto, pageable),
                        request.getRequestURI()
                )
        );
    }
}
