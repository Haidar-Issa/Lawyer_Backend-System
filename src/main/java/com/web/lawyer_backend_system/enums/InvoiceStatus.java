package com.web.lawyer_backend_system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.web.lawyer_backend_system.exception.BadRequestException;

public enum InvoiceStatus {
    REFUNDED,
    PAID,
    PARTIALLY_PAID,
    CANCELLED,
    UNPAID,
    OVERPAID;

    @JsonCreator
    public static InvoiceStatus fromString(String statusStr){
        if(statusStr==null || statusStr.isBlank()){
            return null;
        }
        for(InvoiceStatus status:InvoiceStatus.values()){
            if(status.name().equalsIgnoreCase(statusStr)){
                return status;
            }
        }
        throw new BadRequestException("invalid invoice status: " + statusStr);
    }
}

