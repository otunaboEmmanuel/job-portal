package com.job_portal.job_portal.service;

import com.job_portal.job_portal.dto.EmployerDto;
import com.job_portal.job_portal.dto.UsersDto;
import com.job_portal.job_portal.entity.Employer;
import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.enums.Roles;
import com.job_portal.job_portal.repository.EmployerRepository;
import com.job_portal.job_portal.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UsersServiceImp implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployerRepository employerRepository;

    public UsersServiceImp(UsersRepository usersRepository, PasswordEncoder passwordEncoder, EmployerRepository employerRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
    }

    @Override
    public Users addStudent(UsersDto users) {
        Users users1=usersRepository.findByEmail(users.getEmail()).orElse(null);
        if (users1==null){
             Users newUser=Users.builder()
                    .email(users.getEmail())
                    .userName(users.getUserName())
                    .role(Roles.STUDENT)
                    .password(passwordEncoder.encode(users.getPassword()))
                    .build();
            return usersRepository.save(newUser);
        }else{
            return null;
        }
    }

    @Override
    public Users addEmployer(EmployerDto employerDto) {
        Users users = usersRepository.findByEmail(employerDto.getEmail()).orElse(null);
        if (users == null) {
            Users users1 = Users.builder()
                    .userName(employerDto.getUserName())
                    .email(employerDto.getEmail())
                    .password(passwordEncoder.encode(employerDto.getPassword()))
                    .role(Roles.EMPLOYER)
                    .build();
            usersRepository.save(users1);
            Employer employer=Employer.builder()
                    .user(users1)
                    .companyName(employerDto.getCompanyName())
                    .companyWebsite(employerDto.getCompanyWebsite())
                    .industry(employerDto.getIndustry())
                    .build();
            employerRepository.save(employer);
        }
        return null;
    }
}
