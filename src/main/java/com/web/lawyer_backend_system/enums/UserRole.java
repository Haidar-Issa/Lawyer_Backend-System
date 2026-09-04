package com.web.lawyer_backend_system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    ADMIN,
    USER,
    TRAINER;

    @JsonCreator
    public UserRole fromString(String value) {
        if(value == null || value.isBlank()){
            return null;
        }
        for(UserRole role: UserRole.values()){
            if(role.name().equalsIgnoreCase(value)){
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid UserRole value: " + value);
    }
}
