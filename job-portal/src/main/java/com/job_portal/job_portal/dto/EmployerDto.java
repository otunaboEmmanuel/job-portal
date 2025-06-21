package com.job_portal.job_portal.dto;

import com.job_portal.job_portal.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDto {
    @Email
    @NotBlank(message = "field must not be blank")
    private String email;
    @Size(min=5, max = 50)
    @NotBlank(message = "field must not be blank")
    private String password;
    @NotBlank(message = "field must not be blank")
    private String userName;
    @NotBlank(message = "field must not be blank")
    private Users user;
    @NotBlank(message = "field must not be blank")
    private String companyName;
    @NotBlank(message = "field must not be blank")
    private String industry;
    @NotBlank(message = "field must not be blank")
    private String companyWebsite;
}
