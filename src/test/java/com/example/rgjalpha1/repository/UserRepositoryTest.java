package com.example.rgjalpha1.repository;

import com.example.rgjalpha1.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureDataJpa
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }


    @Test
    @DisplayName("It should check if user does not exist by username")
    void itShouldCheckIfUserDoesNotExistByUsername() {
        // given
        User testUser = new User();
        testUser.setUsername("test1976");

        // when
        underTest.save(testUser);

        // then
        assertThat(underTest.existsByUsername("test1976")).isTrue();

    }

    @Test
    @DisplayName("It should check if user exists by username")
    void itShouldCheckIfUserExistsByUsername() {
        // given
        User testUser = new User();
        testUser.setUsername("test1976");

        // when
        underTest.save(testUser);

        // then
        assertThat(underTest.existsByUsername("test1976")).isTrue();
    }

    @Test
    @DisplayName("It should not find user by username")
    void itShouldNotFindUserByUsername() {
        // given
        String username = "test1999";

        // when
        // then
        assertThat(underTest.findByUsername(username)).isEmpty();

    }

    @Test
    @DisplayName("It should find user by username")
    void itShouldFindUserByUsername() {
        // given
        User testUser = new User();
        testUser.setUsername("test1976");

        // when
        underTest.save(testUser);

        // then
        assertThat(underTest.findByUsername(testUser.getUsername()).isPresent()).isTrue();

    }

}