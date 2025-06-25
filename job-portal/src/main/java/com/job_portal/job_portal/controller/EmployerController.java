package com.job_portal.job_portal.controller;

import com.job_portal.job_portal.entity.JobPost;
import com.job_portal.job_portal.service.JobPostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    private final JobPostService jobPostService;

    public EmployerController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }


    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/createJob")
    public ResponseEntity<?> createJob(@RequestBody @Valid JobPost job){
        Map<String, Object> response=new HashMap<>();
        JobPost jobPost= jobPostService.createJob(job);
        if (jobPost==null){
            response.put("code","100");
            response.put("message","job already exists".toUpperCase());
        }else {
            response.put("code","00");
            response.put("message","job created successfully ".toUpperCase());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
        }


}

