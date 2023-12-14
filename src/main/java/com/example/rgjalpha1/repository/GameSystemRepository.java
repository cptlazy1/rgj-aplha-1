package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.GameSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface GameSystemRepository extends JpaRepository<GameSystem, Long> {
    List<GameSystem> findAllByUserUsername(String username);
}
