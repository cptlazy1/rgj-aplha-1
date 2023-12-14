package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.GameSystemCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameSystemConditionRepository extends JpaRepository<GameSystemCondition, Long> {
}
