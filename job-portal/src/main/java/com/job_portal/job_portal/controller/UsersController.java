package com.job_portal.job_portal.controller;

import com.job_portal.job_portal.dto.LoginDto;
import com.job_portal.job_portal.dto.UsersDto;
import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.repository.UsersRepository;
import com.job_portal.job_portal.service.JwtService;
import com.job_portal.job_portal.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsersController(UsersService usersService, UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody @Valid UsersDto users){
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
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid LoginDto loginDto){
        Map<String, String> response=new HashMap<>();
        Users users=usersRepository.findByEmail(loginDto.getEmail()).orElse(null);
        if (users!=null){
            String dtoPassword= loginDto.getPassword();
            String password=users.getPassword();
            boolean correctPassword=passwordEncoder.matches(dtoPassword,password);
            if (correctPassword){
                String token= jwtService.generateToken(users.getId(),users.getEmail(),users.getUsername(),users.getRole());
                response.put("code","00");
                response.put("message","login success".toUpperCase());
                response.put("token",token);
            }else{
                response.put("code","101");
                response.put("message","password does not match".toUpperCase());
            }
        }else{
            response.put("code","102");
            response.put("message","email does not exist".toUpperCase());
        }
        return ResponseEntity.ok(response);
    }
}
