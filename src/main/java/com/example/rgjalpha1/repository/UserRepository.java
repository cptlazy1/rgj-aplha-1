package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
// This annotation is needed to prevent the PSQLException: Large Objects may not be used in auto-commit mode
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}


