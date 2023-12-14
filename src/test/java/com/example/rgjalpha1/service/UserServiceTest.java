package com.example.rgjalpha1.service;

import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameRepository;
import com.example.rgjalpha1.repository.GameSystemRepository;
import com.example.rgjalpha1.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameSystemRepository gameSystemRepository;
    private AutoCloseable autoCloseable;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository, gameRepository, gameSystemRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllUsers() {
        // given
        List<User> expectedUsers = List.of(new User(), new User(), new User());
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // when
        underTest.getAllUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    void canGetUserByUserName() {
        // given
        User expectedUser = new User();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(expectedUser));

        // when
        underTest.getUserByUserName("username");

        // then
        verify(userRepository).findByUsername("username");

    }

    @Test
    @Disabled
    void uploadProfilePhoto() {
    }

    @Test
    @Disabled
    void uploadGameRoomPhoto() {
    }

    @Test
    @Disabled
    void deleteProfilePhoto() {
    }

    @Test
    @Disabled
    void deleteGameRoomPhoto() {
    }

    @Test
    @Disabled
    void assignGameToUser() {
    }

    @Test
    @Disabled
    void assignGameSystemToUser() {
    }
}