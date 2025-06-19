package com.job_portal.job_portal.controller;

import com.job_portal.job_portal.dto.UsersDto;
import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.service.UsersService;
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
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@Valid @RequestBody UsersDto users){
        Map<String,String> response=new HashMap<>();
          Users users1=usersService.addStudent(users);
          if (users1==null){
              response.put("code","00");
              response.put("message","user already exists".toUpperCase());
          }else {
              response.put("code","100");
              response.put("message","user successfully registered".toUpperCase());
          }
          return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
