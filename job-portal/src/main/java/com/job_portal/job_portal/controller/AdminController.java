package com.job_portal.job_portal.controller;

import com.job_portal.job_portal.dto.EmployerDto;
import com.job_portal.job_portal.dto.PageDto;
import com.job_portal.job_portal.dto.UsersDto;
import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.enums.Roles;
import com.job_portal.job_portal.repository.UsersRepository;
import com.job_portal.job_portal.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UsersService usersService, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateRole/{id}/{role}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @PathVariable String role){
        Map<String,String> response=new HashMap<>();
        Users users=usersRepository.findById(id).orElse(null);
        if (users!=null){
            users.setRole(Roles.valueOf(role.toUpperCase()));
            usersRepository.save(users);
            response.put("code","00");
            response.put("message"," role updated successfully".toUpperCase());
        }else{
            response.put("code","103");
            response.put("message","user does not exist".toUpperCase());
        }
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public Page<?> allUsers(@RequestParam(name = "size",defaultValue = "5",required = false)int size,
                            @RequestParam(name = "page",defaultValue = "0",required = false) int page){
        Pageable pageable= PageRequest.of(page,size, Sort.by("id").descending());
        Page<Users> allUsers= usersRepository.findAllUsers(pageable);
        return allUsers.map(PageDto::new);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UsersDto users,@PathVariable Integer id){
        Map<String,String> response=new HashMap<>();
        Users users1= usersRepository.findById(id).orElse(null);
        if (users1!=null){
            users1.setRole(Roles.STUDENT);
            users1.setUserName(users.getUserName());
            users1.setEmail(users1.getEmail());
            users1.setPassword(passwordEncoder.encode(users.getPassword()));
            usersRepository.save(users1);
            response.put("code","00");
            response.put("message","user successfully updated".toUpperCase());
        }else{
            response.put("code","104");
            response.put("message","user does not exist ".toUpperCase());
        }
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        Map<String,String> response=new HashMap<>();
        Users users=usersRepository.findById(id).orElse(null);
        if (users!=null){
            usersRepository.deleteById(id);
            response.put("code", "00");
            response.put("message","user deleted successfully");
        }else{
            response.put("code", "105");
            response.put("message","user does not exist");
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
