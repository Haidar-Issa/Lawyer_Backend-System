package com.web.lawyer_backend_system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Priority {
    LOW, MEDIUM, HIGH,URGENT;

    @JsonCreator
    public static Priority fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (Priority priority : Priority.values()) {
            if (priority.name().equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown priority: " + value);
    }
}
