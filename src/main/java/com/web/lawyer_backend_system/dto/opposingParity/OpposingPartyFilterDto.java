package com.web.lawyer_backend_system.dto.opposingParity;

import lombok.Data;

@Data
public class OpposingPartyFilterDto {
    private String searchName;
    private String email;
    private String phoneNumber;
    private String lawyerName;
    private String caseId;
}
