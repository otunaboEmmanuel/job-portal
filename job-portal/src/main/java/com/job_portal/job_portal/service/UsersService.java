package com.job_portal.job_portal.service;

import com.job_portal.job_portal.dto.UsersDto;
import com.job_portal.job_portal.entity.Users;
import org.springframework.stereotype.Service;


public interface UsersService {
    Users addStudent(UsersDto users);
}
