package com.job_portal.job_portal.controller;

import com.job_portal.job_portal.dto.EmployerDto;
import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.service.UsersService;
import com.job_portal.job_portal.service.UsersServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UsersService usersService;

    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }


    @PostMapping("/createEmployer")
    public ResponseEntity<?> createEmployer(@RequestBody @Valid EmployerDto employerDto){
        Map<String,String> response=new HashMap<>();
        Users users1=usersService.addEmployer(employerDto);
        if (users1==null){
            response.put("code","00");
            response.put("message","Employer already exists".toUpperCase());
        }else {
            response.put("code","100");
            response.put("message","Employer successfully registered".toUpperCase());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
