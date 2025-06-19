package com.job_portal.job_portal.dto;

import com.job_portal.job_portal.enums.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    @Email
    @NotBlank(message = "field must not be blank")
    private String email;
    @Size(min=5, max = 50)
    @NotBlank(message = "field must not be blank")
    private String password;
    @NotBlank(message = "field must not be blank")
    private String userName;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
