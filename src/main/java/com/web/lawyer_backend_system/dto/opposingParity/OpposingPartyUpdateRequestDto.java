package com.web.lawyer_backend_system.dto.opposingParity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpposingPartyUpdateRequestDto {

    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    private String lawyerName;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid lawyer phone number format")
    private String lawyerPhone;

    private String notes;

    private String caseId;
}
