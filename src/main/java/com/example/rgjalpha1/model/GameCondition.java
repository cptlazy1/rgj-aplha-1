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
@Table(name="game_conditions")
public class GameCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_condition_id")
    private Long gameConditionID;

    private Boolean hasManual;
    private Boolean hasCase;

    private Boolean hasScratches;
    private Boolean hasStickers;
    private Boolean hasWriting;

    @OneToOne(mappedBy = "gameCondition")
    private Game game;
}

