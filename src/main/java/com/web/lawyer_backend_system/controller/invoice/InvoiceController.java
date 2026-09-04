package com.web.lawyer_backend_system.controller.invoice;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.invoice.InvoiceFilterDto;
import com.web.lawyer_backend_system.dto.invoice.InvoiceRequestDto;
import com.web.lawyer_backend_system.dto.invoice.InvoiceResponseDto;
import com.web.lawyer_backend_system.service.invoice.InvoiceService;
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
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> createInvoice(@Valid @RequestBody InvoiceRequestDto invoiceRequestDto,
                                                                         HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "invoice created successfully",
                        invoiceService.create(invoiceRequestDto),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> getInvoice(@PathVariable String invoiceId,
                                                                      HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Invoice by id is retrieved successfully",
                        invoiceService.get(invoiceId),
                        request.getRequestURI()
                )
        );
    }

    @PatchMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> updateInvoice(@PathVariable String invoiceId,
                                                                         @Valid @RequestBody InvoiceRequestDto invoiceRequestDto,
                                                                         HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "invoice by id is updated successfully",
                        invoiceService.update(invoiceId, invoiceRequestDto),
                        request.getRequestURI()
                )
        );
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable String invoiceId, HttpServletRequest request) {
        invoiceService.delete(invoiceId);

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "invoice by Id is deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<InvoiceResponseDto>>> searchInvoices(
            @ModelAttribute InvoiceFilterDto invoiceFilterDto,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Invoices retrieved successfully using filters",
                        invoiceService.searchInvoice(invoiceFilterDto, pageable),
                        request.getRequestURI()
                )
        );
    }



    @GetMapping("/{clientId}/last")
    public ResponseEntity<ApiResponse<List<InvoiceResponseDto>>> getLastInvoicesByClientId(
            @PathVariable String clientId,
            @PageableDefault(size = 1, sort = "paidDate", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Latest invoice(s) retrieved successfully for client: " + clientId,
                        invoiceService.getLastInvoiceByClientId(clientId, pageable),
                        request.getRequestURI()
                )
        );
    }

}
