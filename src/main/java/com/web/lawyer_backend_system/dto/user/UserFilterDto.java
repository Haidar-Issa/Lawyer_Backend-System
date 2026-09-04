package com.web.lawyer_backend_system.dto.user;


import com.web.lawyer_backend_system.enums.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserFilterDto {

    private String fullName;
    @Email(message = "Email should be valid like example@domain.com")
    private String email;
    private UserRole role;
    private Boolean isActive;

}
