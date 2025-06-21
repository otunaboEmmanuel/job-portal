package com.job_portal.job_portal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Email
    @NotBlank(message = "field must not be blank")
    private String email;
    @Size(min=5, max = 50)
    @NotBlank(message = "field must not be blank")
    private String password;
}
