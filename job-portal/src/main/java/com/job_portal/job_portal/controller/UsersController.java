package com.job_portal.job_portal.controller;

import com.job_portal.job_portal.dto.LoginDto;
import com.job_portal.job_portal.dto.UsersDto;
import com.job_portal.job_portal.entity.JobApplication;
import com.job_portal.job_portal.entity.JobPost;
import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.repository.JobPostRepository;
import com.job_portal.job_portal.repository.UsersRepository;
import com.job_portal.job_portal.service.JwtService;
import com.job_portal.job_portal.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final JobPostRepository jobPostRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsersController(UsersService usersService, JobPostRepository jobPostRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usersService = usersService;
        this.jobPostRepository = jobPostRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody @Valid UsersDto users){
        Map<String,String> response=new HashMap<>();
          Users users1=usersService.addStudent(users);
          if (users1==null){
              response.put("code","100");
              response.put("message","user already exists".toUpperCase());
          }else {
              response.put("code","00");
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
    @PostMapping("/apply")
    public ResponseEntity<?> applyForJob(
            @RequestParam("jobId") Integer jobId,
            @RequestParam("studentId") Integer studentId,
            @RequestParam("resume") MultipartFile file, JobApplication jobApplication) throws IOException {
        Map<String, String> response=new HashMap<>();
        Users users=usersRepository.findById(studentId).orElse(null);
        JobPost jobPost=jobPostRepository.findById(jobId).orElse(null);
        if (users==null||jobPost==null){
            response.put("code","103");
            response.put("message","job or user does not exist");
        }

          jobApplication.setJobPost(jobPost);
          jobApplication.setStudent(users);
          return null;
        // Save file locally or to cloud
//        String filePath = fileStorageService.save(file);
//
//        JobApplication application = new JobApplication();
//        application.setJobPost(jobPostRepository.findById(jobId).orElseThrow());
//        application.setStudent(studentRepository.findById(studentId).orElseThrow());
//        application.setResumeUrl(filePath);
//
//        jobApplicationRepository.save(application);
//
//        return ResponseEntity.ok("Application submitted successfully!");
    }

}
