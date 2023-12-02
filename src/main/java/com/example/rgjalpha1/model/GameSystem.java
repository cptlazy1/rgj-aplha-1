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
@Table(name="game_systems")
public class GameSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_system_id")
    Long gameSystemID;
    private String gameSystemName;
    private String gameSystemReview;
    private String gameSystemRating;

    private String gameSystemPhotoFileName;
    @Lob
    private byte[] gameSystemPhotoData;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name")
    private User user;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JoinColumn(name = "game_system_condition_id")
    private GameSystemCondition gameSystemCondition;

}

