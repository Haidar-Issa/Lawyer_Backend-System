package com.web.lawyer_backend_system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    UNDER_REVIEW,
    WAITING_CLIENT,
    WAITING_COURT,
    COMPLETED,
    CANCELLED,
    ON_HOLD;

    @JsonCreator
    public static TaskStatus fromString(String value) {
        if(value == null || value.isBlank()) {
            return null;
        }
        for (TaskStatus status : TaskStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown task status: " + value);
    }
}
