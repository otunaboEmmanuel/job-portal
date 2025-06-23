package com.job_portal.job_portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "field must not be blank")
    private String jobTitle;

    @NotBlank(message = "field must not be blank")
    private String jobRole;

    @NotBlank(message = "field must not be blank")
    private String location;

    @NotBlank(message = "field must not be blank")
    @ElementCollection
    private List<String> requiredSkills;

    @NotBlank(message = "field must not be blank")
    private int experienceRequired; // In years

    @NotBlank(message = "field must not be blank")
    @Column(length = 2000)
    private String jobDescription;

    @NotBlank(message = "field must not be blank")
    private LocalDate postedDate;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @NotBlank(message = "field must not be blank")
    private boolean approved ;

}
