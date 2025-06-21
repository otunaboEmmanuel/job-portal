package com.job_portal.job_portal.service;

import com.job_portal.job_portal.entity.Users;
import com.job_portal.job_portal.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public CustomUserDetailService(UsersRepository usersRepository) {

        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUserName(username).orElse(null);
        if (users != null) {
           return org.springframework.security.core.userdetails.User
                    .withUsername(users.getUsername())
                    .password(users.getPassword())
                    .authorities(getAuthorities(users))
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } else {
            throw new UsernameNotFoundException("user does not exist" + username);
        }
    }
    private Collection<? extends GrantedAuthority> getAuthorities (Users user){
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
}

