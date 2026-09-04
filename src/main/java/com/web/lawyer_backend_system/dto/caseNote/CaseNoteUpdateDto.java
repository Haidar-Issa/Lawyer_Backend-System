package com.web.lawyer_backend_system.dto.caseNote;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CaseNoteUpdateDto {
    @NotBlank(message = "Note text cannot be blank")
    @Size(max = 5000, message = "Note text must not exceed 5000 characters")
    private String text;

    @NotBlank(message = "User ID is required")
    private String userId;
}
