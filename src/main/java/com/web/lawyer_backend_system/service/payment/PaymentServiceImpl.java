package com.web.lawyer_backend_system.service.payment;

import com.web.lawyer_backend_system.dto.payment.PaymentFilterDto;
import com.web.lawyer_backend_system.dto.payment.PaymentRequestDto;
import com.web.lawyer_backend_system.dto.payment.PaymentResponseDto;
import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.entity.Payment;
import com.web.lawyer_backend_system.enums.InvoiceStatus;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.payment.PaymentMapper;
import com.web.lawyer_backend_system.repository.InvoiceRepository;
import com.web.lawyer_backend_system.repository.PaymentRepository;
import com.web.lawyer_backend_system.repository.specification.PaymentSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final InvoiceRepository invoiceRepository;

    @Override
    public PaymentResponseDto recordPayment(PaymentRequestDto dto) {
        Invoice invoice = invoiceRepository.findById(dto.getInvoice())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with ID: " + dto.getInvoice()));

        Payment payment = paymentMapper.toEntity(dto);
        payment.setInvoice(invoice);

        updateInvoiceBalanceForNewPayment(invoice, payment.getAmount());

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponseDto(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentById(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));
        return paymentMapper.toResponseDto(payment);
    }

    @Override
    public PaymentResponseDto updatePayment(String paymentId, PaymentRequestDto dto) {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        BigDecimal oldAmount = existingPayment.getAmount();
        BigDecimal newAmount = dto.getAmount();

        paymentMapper.updateEntityFromDto(dto, existingPayment);

        if (newAmount != null && newAmount.compareTo(oldAmount) != 0) {
            Invoice invoice = existingPayment.getInvoice();
            BigDecimal adjustedPaidAmount = invoice.getPaidAmount().subtract(oldAmount).add(newAmount);
            recalculateInvoiceBalance(invoice, adjustedPaidAmount);
        }

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return paymentMapper.toResponseDto(updatedPayment);
    }

    @Override
    public void cancelPayment(String paymentId) {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        Invoice invoice = existingPayment.getInvoice();
        updateInvoiceBalanceOnPaymentDeletion(invoice, existingPayment.getAmount());

        paymentRepository.delete(existingPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> search(PaymentFilterDto dto, Pageable pageable) {
        Specification<Payment> specification = PaymentSpecifications.build(dto);
        return paymentRepository.findAll(specification, pageable).map(paymentMapper::toResponseDto);
    }

    private void updateInvoiceBalanceForNewPayment(Invoice invoice, BigDecimal paymentAmount) {
        BigDecimal currentPaidAmount = invoice.getPaidAmount() != null ? invoice.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal newPaidAmount = currentPaidAmount.add(paymentAmount);

        recalculateInvoiceBalance(invoice, newPaidAmount);
    }

    private void updateInvoiceBalanceOnPaymentDeletion(Invoice invoice, BigDecimal paymentAmount) {
        BigDecimal currentPaidAmount = invoice.getPaidAmount() != null ? invoice.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal newPaidAmount = currentPaidAmount.subtract(paymentAmount);

        if (newPaidAmount.compareTo(BigDecimal.ZERO) < 0) {
            newPaidAmount = BigDecimal.ZERO;
        }

        recalculateInvoiceBalance(invoice, newPaidAmount);
    }

    private void recalculateInvoiceBalance(Invoice invoice, BigDecimal newPaidAmount) {
        BigDecimal totalAmount = invoice.getAmount() != null ? invoice.getAmount() : BigDecimal.ZERO;

        if (newPaidAmount.compareTo(totalAmount) > 0) {
            throw new IllegalArgumentException("Payment amount cannot exceed the total invoice amount.");
        }

        BigDecimal remainingAmount = totalAmount.subtract(newPaidAmount);
        invoice.setPaidAmount(newPaidAmount);
        invoice.setRemainingAmount(remainingAmount);

        updateInvoiceStatus(invoice, remainingAmount, newPaidAmount);
        invoiceRepository.save(invoice);
    }

    private void updateInvoiceStatus(Invoice invoice, BigDecimal remainingAmount, BigDecimal paidAmount) {
        if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
            invoice.setStatus(InvoiceStatus.OVERPAID);
        } else if (paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        } else {
            invoice.setStatus(InvoiceStatus.UNPAID);
        }
    }
}