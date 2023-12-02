package com.example.rgjalpha1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="game_system_conditions")
public class GameSystemCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_system_condition_id")
    private Long gameSystemConditionID;
    private Boolean hasBox;
    private Boolean hasCables;
    private Boolean isModified;

    @OneToOne(mappedBy = "gameSystemCondition")
    private GameSystem gameSystem;
}
