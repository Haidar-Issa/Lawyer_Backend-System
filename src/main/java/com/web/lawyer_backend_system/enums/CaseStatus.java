package com.web.lawyer_backend_system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CaseStatus {
    NEW,
    IN_PROGRESS,
    PENDING,
    CLOSED,
    ARCHIVED,
    CANCELLED;

    @JsonCreator
    public CaseStatus fromString(String value) {
        if(value == null || value.isBlank()){
            return null;
        }
        for(CaseStatus status: CaseStatus.values()){
            if(status.name().equalsIgnoreCase(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid CaseStatus value: " + value);
    }
}
