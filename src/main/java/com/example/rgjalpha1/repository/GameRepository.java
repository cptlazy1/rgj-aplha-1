package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
