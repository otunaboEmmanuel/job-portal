package com.job_portal.job_portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate applicationDate = LocalDate.now();

    private String status = "PENDING"; // or enum: PENDING, APPROVED, REJECTED

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Users student;

    private String resumeUrl; // This will be the URL or path to the uploaded CV file

    public JobApplication(Integer id) {
        this.id = id;
    }
}
