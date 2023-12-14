package com.example.rgjalpha1.service;

import com.example.rgjalpha1.model.Game;
import com.example.rgjalpha1.model.GameSystem;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameRepository;
import com.example.rgjalpha1.repository.GameSystemRepository;
import com.example.rgjalpha1.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    // Todo: add additional tests for UserService to increase line coverage to 100%

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
    void canUploadProfilePhoto() throws IOException {
        // given
        MultipartFile file = mock(MultipartFile.class);
        User user = new User();
        user.setUsername("testUser");
        String fileName = "testFileName.png";
        byte[] bytes = new byte[20];

        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getBytes()).thenReturn(bytes);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // when
        underTest.uploadProfilePhoto(file, "testUser");

        // then
        verify(userRepository).findByUsername("testUser");
        assertEquals(fileName, user.getProfilePhotoFileName());
        assertEquals(bytes, user.getProfilePhotoData());
        verify(userRepository).save(user);
    }

    @Test
    void uploadGameRoomPhoto() throws IOException {
        // given
        MultipartFile file = mock(MultipartFile.class);
        User user = new User();
        user.setUsername("testUser");
        String fileName = "testFileName.png";
        byte[] bytes = new byte[20];

        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getBytes()).thenReturn(bytes);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // when
        underTest.uploadGameRoomPhoto(file, "testUser");

        // then
        verify(userRepository).findByUsername("testUser");
        assertEquals(fileName, user.getGameRoomPhotoFileName());
        assertEquals(bytes, user.getGameRoomPhotoData());
        verify(userRepository).save(user);


    }

    @Test
    void canDeleteProfilePhoto() {
        // given
        User user = new User();
        user.setUsername("testUser");
        user.setProfilePhotoFileName("testFileName.png");
        user.setProfilePhotoData(new byte[20]);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // when
        underTest.deleteProfilePhoto("testUser");

        // then
        verify(userRepository).findByUsername("testUser");
        assertNull(user.getProfilePhotoFileName());
        assertNull(user.getProfilePhotoData());
        verify(userRepository).save(user);
    }

    @Test
    void deleteGameRoomPhoto() {
        // given
        User user = new User();
        user.setUsername("testUser");
        user.setGameRoomPhotoFileName("testFileName.png");
        user.setGameRoomPhotoData(new byte[20]);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // when
        underTest.deleteGameRoomPhoto("testUser");

        // then
        verify(userRepository).findByUsername("testUser");
        assertNull(user.getGameRoomPhotoFileName());
        assertNull(user.getGameRoomPhotoData());
        verify(userRepository).save(user);
    }

    @Test
    void canAssignGameToUser() {
        // given
        User user = new User();
        Game game = new Game();
        user.setUsername("testUser");
        Long gameID = 1L;

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameID)).thenReturn(Optional.of(game));

        // when
        underTest.assignGameToUser("testUser", gameID);

        // then
        verify(userRepository).findByUsername("testUser");
        verify(gameRepository).findById(1L);
        assertTrue(user.getGames().contains(game));
        assertEquals(user, game.getUser());
        verify(userRepository).save(user);
    }

    @Test
    void canAssignGameSystemToUser() {
        // given
        User user = new User();
        GameSystem gameSystem = new GameSystem();
        user.setUsername("testUser");
        Long gameSystemID = 1L;

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.of(gameSystem));

        // when
        underTest.assignGameSystemToUser("testUser", gameSystemID);

        // then
        verify(userRepository).findByUsername("testUser");
        verify(gameSystemRepository).findById(gameSystemID);
        assertTrue(user.getGameSystems().contains(gameSystem));
        assertEquals(user, gameSystem.getUser());
        verify(userRepository).save(user);

    }
}