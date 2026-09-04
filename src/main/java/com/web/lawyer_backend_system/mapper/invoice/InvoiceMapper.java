package com.web.lawyer_backend_system.mapper.invoice;

import com.web.lawyer_backend_system.dto.invoice.InvoiceRequestDto;
import com.web.lawyer_backend_system.dto.invoice.InvoiceResponseDto;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.Invoice;
import com.web.lawyer_backend_system.mapper.cases.CaseMapper;
import com.web.lawyer_backend_system.mapper.client.ClientMapper;
import com.web.lawyer_backend_system.repository.CaseRepository;
import com.web.lawyer_backend_system.repository.ClientRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, CaseMapper.class})
public abstract class InvoiceMapper {

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected CaseRepository caseRepository;

    @Mapping(source = "client", target = "client")
    @Mapping(source = "caseId", target = "caseId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "invoiceId", ignore = true)
    @Mapping(target = "remainingAmount",ignore = true)
    @Mapping(target = "isDelete" ,ignore = true)
    public abstract Invoice toInvoice(InvoiceRequestDto invoiceRequestDto);

    @Mapping(source = "client.clientId", target = "clientId")
    @Mapping(source = "client.fullName" , target = "clientName")
    @Mapping(source = "client.email", target = "clientEmail")
    public abstract InvoiceResponseDto toInvoiceResponseDto(Invoice invoice);

    @Mapping(source = "client", target = "client")
    @Mapping(source = "caseId", target = "caseId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "invoiceId", ignore = true)
    @Mapping(target = "remainingAmount",ignore = true)
    @Mapping(target = "isDelete" ,ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(InvoiceRequestDto invoiceRequestDto, @MappingTarget Invoice invoice);

    protected Client mapStringToClient(String clientId) {
        if (clientId == null || clientId.isBlank()) {
            return null;
        }
        return clientRepository.findById(clientId).orElse(null);
    }

    protected Case_ mapStringToCase(String caseId) {
        if (caseId == null || caseId.isBlank()) {
            return null;
        }
        return caseRepository.findById(caseId).orElse(null);
    }

    protected String mapClientToString(Client client) {
        return client == null ? null : client.getClientId();
    }

    protected String mapCaseToString(Case_ caseEntity) {
        return caseEntity == null ? null : caseEntity.getCaseId();
    }
}
