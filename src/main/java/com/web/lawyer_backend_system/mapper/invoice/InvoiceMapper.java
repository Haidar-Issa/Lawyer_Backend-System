package com.web.lawyer_backend_system.mapper.invoice;

import com.web.lawyer_backend_system.dto.invoice.InvoiceRequestDto;
import com.web.lawyer_backend_system.dto.invoice.InvoiceResponseDto;
import com.web.lawyer_backend_system.mapper.client.ClientMapper;
import com.web.lawyer_backend_system.entity.Invoice;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface InvoiceMapper {
    @Mapping(source = "notes" , target = "notes")
    @Mapping(source = "client" , target ="client.clientId" )
    Invoice toInvoice(InvoiceRequestDto invoiceRequestDto);

    @Mapping(source = "client" , target = "client")
    InvoiceResponseDto toInvoiceResponseDto(Invoice invoice);

    @Mapping(source = "client", target = "client.clientId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(InvoiceRequestDto invoiceRequestDto, @MappingTarget Invoice invoice);
}
