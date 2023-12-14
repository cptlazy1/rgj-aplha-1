package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.GameCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameConditionRepository extends JpaRepository<GameCondition, Long> {
}
