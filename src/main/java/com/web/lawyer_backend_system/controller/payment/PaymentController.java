package com.web.lawyer_backend_system.controller.payment;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.payment.PaymentFilterDto;
import com.web.lawyer_backend_system.dto.payment.PaymentRequestDto;
import com.web.lawyer_backend_system.dto.payment.PaymentResponseDto;
import com.web.lawyer_backend_system.service.payment.PaymentService;
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
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponseDto>> recordPayment(
            @Valid @RequestBody PaymentRequestDto paymentDto,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Payment recorded successfully",
                        paymentService.recordPayment(paymentDto),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> getPaymentById(
            @PathVariable String paymentId,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Payment retrieved successfully",
                        paymentService.getPaymentById(paymentId),
                        request.getRequestURI()
                )
        );
    }

    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @PutMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> updatePayment(
            @PathVariable String paymentId,
            @Valid @RequestBody PaymentRequestDto paymentDto,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Payment updated successfully",
                        paymentService.updatePayment(paymentId, paymentDto),
                        request.getRequestURI()
                )
        );
    }

//    @PreAuthorize("hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<Void>> cancelPayment(
            @PathVariable String paymentId,
            HttpServletRequest request) {

        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Payment cancelled successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PaymentResponseDto>>> searchPayments(
            @ModelAttribute PaymentFilterDto paymentFilterDto,
            @PageableDefault( sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Payments retrieved successfully using filter criteria",
                        paymentService.search(paymentFilterDto, pageable),
                        request.getRequestURI()
                )
        );
    }
}