package com.web.lawyer_backend_system.enums;


import com.fasterxml.jackson.annotation.JsonCreator;

public enum AppointmentType {
    COURT_HEARING,
    CLIENT_MEETING,
    CONSULTATION,
    DEPOSITION,
    MEDIATION,
    CASE_REVIEW,
    DEADLINE,
    REMINDER;

    @JsonCreator
    public AppointmentType forValue(String value) {
        if(value == null){
            return null;
        }
        for(AppointmentType type : AppointmentType.values()){
            if(type.name().equalsIgnoreCase(value)){
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown Type: "+ value);
    }
}
