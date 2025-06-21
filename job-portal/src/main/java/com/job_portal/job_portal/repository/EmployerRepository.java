package com.job_portal.job_portal.repository;

import com.job_portal.job_portal.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,Integer> {
}
