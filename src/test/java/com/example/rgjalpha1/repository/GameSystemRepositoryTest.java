package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.GameSystem;
import com.example.rgjalpha1.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureDataJpa
public class GameSystemRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSystemRepository underTest;

    @AfterEach
    public void tearDown() {
        underTest.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void itShouldFindAllGameSystemsByUserUsername() {
        // given
        User testUser = new User();
        testUser.setUsername("test1976");
        testUser = userRepository.save(testUser);

        GameSystem testGameSystem = new GameSystem();
        testGameSystem.setGameSystemName("testSystem");
        testGameSystem.setUser(testUser);
        underTest.save(testGameSystem);

        // when
        List<GameSystem> gameSystems = underTest.findAllByUserUsername("test1976");

        // then
        assertThat(gameSystems).contains(testGameSystem);
    }


}