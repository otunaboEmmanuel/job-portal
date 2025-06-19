package com.job_portal.job_portal.service;

import com.job_portal.job_portal.dto.UsersDto;
import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.enums.Roles;
import com.job_portal.job_portal.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UsersServiceImp implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImp(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users addStudent(UsersDto users) {
        Users users1=usersRepository.findByEmail(users.getEmail()).orElse(null);
        if (users1==null){
            return Users.builder()
                    .email(users.getEmail())
                    .userName(users.getUserName())
                    .role(Roles.STUDENT)
                    .password(passwordEncoder.encode(users.getPassword()))
                    .build();
        }else{ return null;
        }
    }
}
