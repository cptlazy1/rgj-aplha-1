package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.GameSystem;
import com.example.rgjalpha1.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GameSystemRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSystemRepository gameSystemRepository;

    @AfterEach
    public void tearDown() {
        gameSystemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testFindAllByUserUsername() {
        // given
        User user = new User();
        user.setUsername("testUser");

        GameSystem gameSystem = new GameSystem();
        gameSystem.setUser(user);
        user.getGameSystems().add(gameSystem);

        userRepository.save(user);
        gameSystemRepository.save(gameSystem);

        // when

        List<GameSystem> found = gameSystemRepository.findAllByUserUsername(user.getUsername());
        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getUser()).isEqualTo(user);
    }
}