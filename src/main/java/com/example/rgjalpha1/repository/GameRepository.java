package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {
}
