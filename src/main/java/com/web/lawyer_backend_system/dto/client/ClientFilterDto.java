package com.web.lawyer_backend_system.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class ClientFilterDto {
    private String clientId;
    private String fullName;
    private String email;
    private BigInteger nationalNumber;
    private BigInteger phoneNumber;
    private String lawyerId;
}
