package com.job_portal.job_portal.repository;

import com.job_portal.job_portal.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByUserName(String username);

    Optional<Users> findByEmail(String email);

    @Query("""
            SELECT user
            FROM Users user
            """)
    Page<Users> findAllUsers(Pageable pageable);
}
