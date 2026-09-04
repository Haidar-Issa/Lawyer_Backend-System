package com.web.lawyer_backend_system.service.payment;

import com.web.lawyer_backend_system.dto.payment.PaymentFilterDto;
import com.web.lawyer_backend_system.dto.payment.PaymentRequestDto;
import com.web.lawyer_backend_system.dto.payment.PaymentResponseDto;
import com.web.lawyer_backend_system.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    PaymentResponseDto recordPayment(PaymentRequestDto dto);

    PaymentResponseDto getPaymentById(String paymentId);

    PaymentResponseDto updatePayment(String paymentId, PaymentRequestDto dto);

    void cancelPayment(String paymentId);

    Page<PaymentResponseDto> search(PaymentFilterDto dto, Pageable pageable);
}
