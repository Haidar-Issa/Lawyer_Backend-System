package com.web.lawyer_backend_system.dto.cases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaseRequestDto {

    @NotBlank(message = "title is required!")
    @Size(min = 3 , max = 50)
    private String title;

    @NotBlank(message = "description is required!")
    @Size(min = 10,max = 100)
    private String description;

    private String caseStatus;

    @NotBlank(message = "court Name is required!")
    @Size(min = 3 , max = 50)
    private String courtName;

    @NotBlank(message = "Creator Id is required!")
    private String creator;

    @NotBlank(message = "assignedLawyer Id is required!")
    private String assignedLawyer;

    @NotBlank(message = "Client Id is required!")
    private String client;


    private LocalDateTime startDate;
}
