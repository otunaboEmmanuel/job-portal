package com.job_portal.job_portal.service;

import com.job_portal.job_portal.entity.JobPost;
import com.job_portal.job_portal.repository.JobPostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JobServiceImp implements JobPostService{
    private final JobPostRepository jobPostRepository;

    public JobServiceImp(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    public JobPost createJob(JobPost job) {
         JobPost jobPost=jobPostRepository.findByJobTitle(job.getJobTitle()).orElse(null);
         if (jobPost==null){
             JobPost jobPost1=JobPost.builder()
                     .jobDescription(job.getJobDescription())
                     .jobRole(job.getJobRole())
                     .approved(false)
                     .employer(job.getEmployer())
                     .postedDate(LocalDate.now())
                     .jobTitle(job.getJobTitle())
                     .experienceRequired(job.getExperienceRequired())
                     .location(job.getLocation())
                     .requiredSkills(job.getRequiredSkills())
                     .build();
             return jobPostRepository.save(jobPost1);
         }else{
             return null;
         }
    }
}
