package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.GameSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface GameSystemRepository extends JpaRepository<GameSystem, Long> {
    List<GameSystem> findAllByUserUsername(String username);
}
