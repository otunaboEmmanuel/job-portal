package com.job_portal.job_portal.dto;

import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

    private String email;
    private Integer id;
    private String userName;
    private Roles role;

    public PageDto(Users users) {
        this.email=users.getEmail();
        this.id= users.getId();
        this.userName=users.getUsername();
        this.role=users.getRole();
    }
}
