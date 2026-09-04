package com.web.lawyer_backend_system.mapper.payment;

import com.web.lawyer_backend_system.dto.payment.PaymentRequestDto;
import com.web.lawyer_backend_system.dto.payment.PaymentResponseDto;
import com.web.lawyer_backend_system.entity.Payment;
import com.web.lawyer_backend_system.mapper.invoice.InvoiceMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {InvoiceMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    @Mapping(target = "invoice.invoiceId", source = "invoice")
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Payment toEntity(PaymentRequestDto dto);

    @Mapping(target = "invoice", source = "invoice.invoiceId")
    PaymentResponseDto toResponseDto(Payment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "invoice.invoiceId", source = "invoice")
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(PaymentRequestDto dto, @MappingTarget Payment entity);
}
