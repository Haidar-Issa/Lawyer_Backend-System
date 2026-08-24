package com.web.lawyer_backend_system.service.invoice;

import com.web.lawyer_backend_system.dto.invoice.InvoiceRequestDto;
import com.web.lawyer_backend_system.dto.invoice.InvoiceResponseDto;
import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.mapper.invoice.InvoiceMapper;
import com.web.lawyer_backend_system.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceResponseDto create(InvoiceRequestDto invoiceRequestDto) {
        Invoice invoice = invoiceMapper.toInvoice(invoiceRequestDto);
        Invoice invoice1 = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoiceResponseDto(invoice1);
    }

    public InvoiceResponseDto update(String invoiceId, InvoiceRequestDto invoiceRequestDto) {
        Invoice existingInvoice = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new EntityNotFoundException("invoice not found"));
        invoiceMapper.updateFromDto(invoiceRequestDto, existingInvoice);
        return invoiceMapper.toInvoiceResponseDto(existingInvoice);
    }

    public InvoiceResponseDto get(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new EntityNotFoundException("invoice not found")
        );
        return invoiceMapper.toInvoiceResponseDto(invoice);
    }

}
