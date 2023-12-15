package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByUserUsername(String username);

}
