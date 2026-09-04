package com.web.lawyer_backend_system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AppointmentStatus {
    SCHEDULED,
    CONFIRMED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    NO_SHOW,
    RESCHEDULED,
    POSTPONED;

    @JsonCreator
    public AppointmentStatus fromString(String value) {
        if(value == null) return null;
        for(AppointmentStatus status : AppointmentStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status:" + value);
    }
}
