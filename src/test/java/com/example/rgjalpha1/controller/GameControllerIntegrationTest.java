package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.model.Game;
import com.example.rgjalpha1.model.GameCondition;
import com.example.rgjalpha1.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class GameControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager testEntityManager;

    @BeforeEach
    void clearDb() {
        testEntityManager.clear();
    }

    @Test
    @DisplayName("Should add game and game condition and assign to user")
    void shouldAddGameAndGameConditionAndAssignToUser() throws Exception {

        User user = new User();
        user.setUsername("testUser");
        testEntityManager.persist(user);
        String requestJson = """
            {
                "gameDto": {
                    "gameName": "Sonic 666",
                    "gameYearOfRelease": 1993,
                    "gamePublisher": "Sega",
                    "gameIsOriginal": true,
                    "systemName": "MegaDrive"
                },
                "gameConditionDto": {
                    "hasManual": true,
                    "hasCase": true,
                    "hasScratches": false,
                    "hasStickers": false,
                    "hasWriting": false
                }
            }
        """;

        this.mockMvc
                .perform(post("/users/{username}/games", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Sonic 666 added successfully to user " + user.getUsername())));

    }

    @Test
    @DisplayName("Should get all games and game conditions")
    void shouldGetAllGamesAndGameConditions() throws Exception {
        Game game = new Game();
        game.setGameName("Sonic 666");
        game.setGameYearOfRelease(1993);
        game.setGamePublisher("Sega");
        game.setGameIsOriginal(true);
        game.setSystemName("MegaDrive");
        testEntityManager.persist(game);

        Game game2 = new Game();
        game2.setGameName("Mario 777");
        game2.setGameYearOfRelease(1994);
        game2.setGamePublisher("Nintendo");
        game2.setGameIsOriginal(true);
        game2.setSystemName("SNES");
        testEntityManager.persist(game2);

        GameCondition gameCondition = new GameCondition();
        gameCondition.setHasManual(true);
        gameCondition.setHasCase(true);
        gameCondition.setHasScratches(false);
        gameCondition.setHasStickers(false);
        gameCondition.setHasWriting(false);
        testEntityManager.persist(gameCondition);

        GameCondition gameCondition2 = new GameCondition();
        gameCondition2.setHasManual(true);
        gameCondition2.setHasCase(true);
        gameCondition2.setHasScratches(false);
        gameCondition2.setHasStickers(false);
        gameCondition2.setHasWriting(false);
        testEntityManager.persist(gameCondition2);

        testEntityManager.flush();

        this.mockMvc
                .perform(get("/admin/games"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());


    }

    @Test
    void shouldUploadGamePhoto() throws Exception {
        String originalFilename = "testPhoto.jpg";
        String contentType = "image/jpeg";

        byte[] content = new byte[20];
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", originalFilename, contentType, content);

        User user = new User();
        user.setUsername("testUser");
        testEntityManager.persist(user);

        Game game = new Game();
        game.setGameName("Shadow of the Beast");
        game.setUser(user);
        testEntityManager.persist(game);

        testEntityManager.flush();

        this.mockMvc
                .perform(multipart("/users/{username}/games/{gameID}/upload-game-photo", "testUser", game.getGameID())
                        .file(mockMultipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value(originalFilename))
                .andExpect(jsonPath("$.contentType").value(contentType));
    }

    @Test
    @DisplayName("Should get all users games")
    void shouldGetAllUsersGames() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        testEntityManager.persist(user);

        Game game = new Game();
        game.setGameName("Shadow of the Beast");
        game.setUser(user);
        testEntityManager.persist(game);

        Game game2 = new Game();
        game2.setGameName("Shadow of the Beast 2");
        game2.setUser(user);
        testEntityManager.persist(game2);

        testEntityManager.flush();

        this.mockMvc
                .perform(get("/users/{username}/games", "testUser"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameName").value("Shadow of the Beast"))
                .andExpect(jsonPath("$[1].gameName").value("Shadow of the Beast 2"));
    }

}