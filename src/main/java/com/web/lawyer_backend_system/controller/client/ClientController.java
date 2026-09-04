package com.web.lawyer_backend_system.controller.client;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.client.ClientFilterDto;
import com.web.lawyer_backend_system.dto.client.ClientRequestDto;
import com.web.lawyer_backend_system.dto.client.ClientResponseDto;
import com.web.lawyer_backend_system.service.client.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponseDto>> createClient(
            @Valid @RequestBody ClientRequestDto clientRequestDto,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Client created successfully",
                        clientService.createClient(clientRequestDto),
                        request.getRequestURI()
                )
        );
    }

    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(
            @PathVariable String clientId,
            HttpServletRequest request) {

        clientService.deleteClient(clientId);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientById(
            @PathVariable String clientId,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client retrieved successfully",
                        clientService.getClientById(clientId),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ClientResponseDto>>> searchClient(
            @ModelAttribute ClientFilterDto clientFilterDto,
            @PageableDefault(page = 0 ,size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Clients retrieved successfully using filter criteria",
                        clientService.filterClients(clientFilterDto, pageable),
                        request.getRequestURI()
                )
        );
    }
}