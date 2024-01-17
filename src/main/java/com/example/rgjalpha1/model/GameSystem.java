package com.example.rgjalpha1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
    private String gameSystemBrand;
    private int gameSystemYearOfRelease;
    private Boolean isReadyToPlay;

    @Lob
    private byte[] gameSystemPhotoData;
    private String gameSystemPhotoFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name")
    private User user;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JoinColumn(name = "game_system_condition_id")
    private GameSystemCondition gameSystemCondition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GameSystem that = (GameSystem) o;
        return Objects.equals(gameSystemID, that.gameSystemID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameSystemID);
    }

}

