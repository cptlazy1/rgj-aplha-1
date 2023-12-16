package com.example.rgjalpha1.service;

import com.example.rgjalpha1.dto.GameSystemDto;
import com.example.rgjalpha1.exception.BadRequestException;
import com.example.rgjalpha1.exception.RecordNotFoundException;
import com.example.rgjalpha1.model.GameSystem;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameSystemRepository;
import com.example.rgjalpha1.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // This is needed to use Mockito
class GameSystemServiceTest {

    @Mock
    private GameSystemRepository gameSystemRepository;
    @Mock
    private UserRepository userRepository;
    private GameSystemService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = Mockito.spy(new GameSystemService(gameSystemRepository, userRepository));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    @DisplayName("It should add game system")
    void itShouldAddGameSystem() {
        // given
        GameSystemDto gameSystemDto = new GameSystemDto();
        gameSystemDto.setGameSystemName("Test Game System");

        GameSystem gameSystem = new GameSystem();
        gameSystem.setGameSystemName("Test Game System");

        GameSystem savedGameSystem = new GameSystem();
        savedGameSystem.setGameSystemName("Test Game System");
        savedGameSystem.setGameSystemID(1L);

        when(gameSystemRepository.save(gameSystem)).thenReturn(savedGameSystem);

        // when
        GameSystemDto expected = underTest.addGameSystem(gameSystemDto);

        // then
        gameSystemDto.setGameSystemID(1L);
        gameSystemDto.setGameSystemYearOfRelease(0); // this field is an Integer, so it will be null if it is not set
        assertThat(expected).isEqualTo(gameSystemDto);
        verify(gameSystemRepository).save(any(GameSystem.class));
    }

    @Test
    @DisplayName("It should throw BadRequestException when game system name is null")
    void itShouldThrowBadRequestExceptionWhenGameSystemNameIsNull() {
        // given
        GameSystemDto gameSystemDto = new GameSystemDto();

        // when
        // then
        assertThrows(BadRequestException.class, () -> underTest.addGameSystem(gameSystemDto));
    }

    @Test
    @DisplayName("It should get all game systems")
    void itShouldGetAllGameSystems() {
        // given
        GameSystem gameSystem1 = new GameSystem();
        gameSystem1.setGameSystemName("Test Game System 1");

        GameSystem gameSystem2 = new GameSystem();
        gameSystem2.setGameSystemName("Test Game System 2");

        List<GameSystem> gameSystems = Arrays.asList(gameSystem1, gameSystem2);

        when(gameSystemRepository.findAll()).thenReturn(gameSystems);

        // when
        List<GameSystemDto> result = underTest.getAllGameSystems();

        // then
        assertThat(result.size()).isEqualTo(2);
        verify(gameSystemRepository).findAll();
    }

    @Test
    @DisplayName("It should get all game systems of a user")
    void canGetAllGameSystemsOfUser() {
        // given
        String username = "testuser";

        GameSystem gameSystem1 = new GameSystem();
        gameSystem1.setGameSystemName("Test Game System 1");
        GameSystem gameSystem2 = new GameSystem();
        gameSystem2.setGameSystemName("Test Game System 2");

        List<GameSystem> gameSystems = Arrays.asList(gameSystem1, gameSystem2);

        when(gameSystemRepository.findAllByUserUsername(username)).thenReturn(gameSystems);

        // when
        List<GameSystemDto> expected = underTest.getAllGameSystemsOfUser(username);

        // then
        assertThat(expected.size()).isEqualTo(2);
        verify(gameSystemRepository).findAllByUserUsername(username);
    }


    @Test
    @DisplayName("It should update game system with all fields")
    void itShouldUpdateGameSystemWithAllFields() {
        // given
        Long gameSystemID = 1L;
        GameSystemDto gameSystemDto = new GameSystemDto();
        gameSystemDto.setGameSystemName("Updated Game System");
        gameSystemDto.setGameSystemBrand("Updated Brand");
        gameSystemDto.setGameSystemYearOfRelease(2022);
        gameSystemDto.setIsReadyToPlay(true);

        GameSystem gameSystem = new GameSystem();

        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.of(gameSystem));
        when(gameSystemRepository.save(any(GameSystem.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        GameSystemDto result = underTest.updateGameSystem(gameSystemID, gameSystemDto);

        // then
        assertThat(result).isEqualTo(gameSystemDto);
        verify(gameSystemRepository).findById(gameSystemID);
        verify(gameSystemRepository).save(gameSystem);
    }

    @Test
    @DisplayName("It should update game system with some fields null")
    void itShouldUpdateGameSystemWithSomeFieldsNull() {
        // given
        Long gameSystemID = 1L;
        GameSystemDto gameSystemDto = new GameSystemDto();
        gameSystemDto.setGameSystemName("Updated Game System");

        GameSystem gameSystem = new GameSystem();

        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.of(gameSystem));
        when(gameSystemRepository.save(any(GameSystem.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        GameSystemDto result = underTest.updateGameSystem(gameSystemID, gameSystemDto);

        // then
        assertThat(result.getGameSystemName()).isEqualTo(gameSystemDto.getGameSystemName());
        verify(gameSystemRepository).findById(gameSystemID);
        verify(gameSystemRepository).save(gameSystem);
    }

    @Test
    @DisplayName("It should throw RecordNotFoundException when game system does not exist")
    void itShouldThrowRecordNotFoundExceptionWhenGameSystemDoesNotExist() {
        // given
        Long gameSystemID = 1L;
        GameSystemDto gameSystemDto = new GameSystemDto();

        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.empty());

        // then
        assertThrows(RecordNotFoundException.class, () -> underTest.updateGameSystem(gameSystemID, gameSystemDto));
    }


    @Test
    @DisplayName("It should delete game system")
    void itShouldDeleteGameSystem() {
        // given
        String username = "testuser";
        Long gameSystemID = 1L;

        GameSystem gameSystem = new GameSystem();
        gameSystem.setGameSystemName("Test Game System");

        User user = new User();
        user.setUsername(username);

        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.of(gameSystem));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when
        underTest.deleteGameSystem(username, gameSystemID);

        // then
        verify(gameSystemRepository).deleteById(gameSystemID);

    }

    @Test
    @DisplayName("It should throw RecordNotFoundException when game system does not exist")
    void itShouldThrowRecordNotFoundExceptionWhenDeleteGameSystemDoesNotExist() {
        // given
        String username = "testUser";
        Long gameSystemID = 1L;

        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.empty());

        // then
        assertThrows(RecordNotFoundException.class, () -> underTest.deleteGameSystem(username, gameSystemID));
    }

    @Test
    @DisplayName("Can upload game system photo")
    void canUploadGameSystemPhoto() throws IOException {
        // given
        MultipartFile file = mock(MultipartFile.class);
        Long gameSystemID = 1L;
        String username = "testUser";
        String fileName = "testFileName.png";
        byte[] bytes = new byte[20];

        GameSystem gameSystem = new GameSystem();
        User user = new User();
        user.setUsername(username);

        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getBytes()).thenReturn(bytes);
        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.of(gameSystem));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when
        underTest.uploadGameSystemPhoto(file, gameSystemID, username);

        // then
        verify(gameSystemRepository).findById(gameSystemID);
        verify(userRepository).findByUsername(username);
        assertEquals(fileName, gameSystem.getGameSystemPhotoFileName());
        assertEquals(bytes, gameSystem.getGameSystemPhotoData());
        verify(gameSystemRepository).save(gameSystem);
    }

    @Test
    @DisplayName("Throws exception when no game system exists for given gameSystemID when uploading game system photo")
    void canUploadGameSystemPhotoThrowsRecordNotFoundException() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        Long gameSystemID = 1L;
        String username = "testUser";

        when(file.getOriginalFilename()).thenReturn("testFileName.png");
        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.empty());

        // then
        assertThrows(RecordNotFoundException.class, () -> underTest.uploadGameSystemPhoto(file, gameSystemID, username));
    }

    @Test
    @DisplayName("Can upload game system photo throws RecordNotFoundException for user")
    void canUploadGameSystemPhotoThrowsRecordNotFoundExceptionForUser() throws RecordNotFoundException {
        // given
        MultipartFile file = mock(MultipartFile.class);
        Long gameSystemID = 1L;
        String username = "testUser";

        GameSystem gameSystem = new GameSystem();

        when(file.getOriginalFilename()).thenReturn("testFileName.png");
        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.of(gameSystem));
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // then
        assertThrows(RecordNotFoundException.class, () -> underTest.uploadGameSystemPhoto(file, gameSystemID, username));
    }

    @Test
    @DisplayName("Can upload game system photo throws IOException")
    void canUploadGameSystemPhotoThrowsIOException() throws IOException {
        // given
        MultipartFile file = mock(MultipartFile.class);
        Long gameSystemID = 1L;
        String username = "testUser";
        String fileName = "testFileName.png";

        GameSystem gameSystem = new GameSystem();
        User user = new User();
        user.setUsername(username);

        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getBytes()).thenThrow(new IOException("Test exception"));
        when(gameSystemRepository.findById(gameSystemID)).thenReturn(Optional.of(gameSystem));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // then
        assertThrows(IOException.class, () -> underTest.uploadGameSystemPhoto(file, gameSystemID, username));
    }

}