package com.web.lawyer_backend_system.service.invoice;

import com.web.lawyer_backend_system.dto.invoice.InvoiceFilterDto;
import com.web.lawyer_backend_system.dto.invoice.InvoiceRequestDto;
import com.web.lawyer_backend_system.dto.invoice.InvoiceResponseDto;
import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.enums.InvoiceStatus;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.invoice.InvoiceMapper;
import com.web.lawyer_backend_system.repository.InvoiceRepository;
import com.web.lawyer_backend_system.repository.specification.InvoiceSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;


    @Transactional
    public InvoiceResponseDto create(InvoiceRequestDto invoiceRequestDto) {
        Invoice invoice = invoiceMapper.toInvoice(invoiceRequestDto);
        Invoice invoice1 = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoiceResponseDto(invoice1);
    }

    public InvoiceResponseDto update(String invoiceId, InvoiceRequestDto invoiceRequestDto) {
        Invoice existingInvoice = invoiceRepository.findByInvoiceId(invoiceId).orElseThrow(
                () -> new EntityNotFoundException("invoice not found by id: " + invoiceId));
        invoiceMapper.updateFromDto(invoiceRequestDto, existingInvoice);
        return invoiceMapper.toInvoiceResponseDto(existingInvoice);
    }


    public InvoiceResponseDto get(String invoiceId) {
        Invoice invoice = invoiceRepository.findByInvoiceId(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("invoice not found By id: " + invoiceId)
        );
        return invoiceMapper.toInvoiceResponseDto(invoice);
    }

    @Transactional
    public void delete(String invoiceId) {
        if (!invoiceRepository.existsById(invoiceId)) {
            throw new ResourceNotFoundException("Invoice not found with id: " + invoiceId);
        }

        invoiceRepository.deleteByInvoiceId(invoiceId);
        log.info("Deleted invoice with id: {}", invoiceId);
    }

    //Dynamic Query
    public Page<InvoiceResponseDto> searchInvoice(InvoiceFilterDto invoiceFilterDto, Pageable pageable) {
        Specification<Invoice> spec = Specification.where(InvoiceSpecifications.fetchRelations())
                .and(InvoiceSpecifications.hasPaidBetween(invoiceFilterDto.getStartDate(), invoiceFilterDto.getEndDate()))
                .and(InvoiceSpecifications.hasIssueDate(invoiceFilterDto.getIssueDate()))
                .and(InvoiceSpecifications.hasDueDate(invoiceFilterDto.getDueDate()))
                .and(InvoiceSpecifications.hasInvoiceStatus(InvoiceStatus.fromString(invoiceFilterDto.getStatus())))
                .and(InvoiceSpecifications.hasCaseId(invoiceFilterDto.getCaseId()))
                .and(InvoiceSpecifications.hasInvoiceId(invoiceFilterDto.getInvoiceId()))
                .and(InvoiceSpecifications.hasClientId(invoiceFilterDto.getClientId()))
                .and(InvoiceSpecifications.hasNote(invoiceFilterDto.getNote()));

        return invoiceRepository.findAll(spec, pageable)
                .map(invoiceMapper::toInvoiceResponseDto);
    }


    public List<InvoiceResponseDto> getLastInvoiceByClientId(String clientId, Pageable pageable) {
        List<Invoice> invoices = invoiceRepository.findLatestInvoicesByClientId(clientId, pageable)
                .orElseThrow(() -> new ResourceNotFoundException("Client Id is not found: " + clientId));
        return invoices.stream()
                .map(invoiceMapper::toInvoiceResponseDto)
                .collect(Collectors.toList());
    }


}
