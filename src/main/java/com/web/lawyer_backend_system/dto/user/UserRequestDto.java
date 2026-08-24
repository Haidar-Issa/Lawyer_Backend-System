package com.web.lawyer_backend_system.dto.user;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@RequiredArgsConstructor
@Data
public class UserRequestDto {
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100 , message = "Full name must be between 3 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    @NotNull(message = "Phone is required")
    @Digits(integer = 9, fraction = 0, message = "Phone number must be a valid number with up to 9 digits")
    private BigInteger phoneNumber;

    @NotNull(message = "National number is required")
    @Digits(integer = 10, fraction = 0, message = "National number must be a valid number with up to 10 digits")
    private BigInteger nationalNumber;

    private boolean isActive;
}
