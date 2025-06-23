package com.job_portal.job_portal.repository;

import com.job_portal.job_portal.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost,Integer> {
    Optional<JobPost> findByJobTitle(String jobTitle);
}
