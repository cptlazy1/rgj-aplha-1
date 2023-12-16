package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.dto.GameDto;
import com.example.rgjalpha1.security.JwtService;
import com.example.rgjalpha1.service.GameConditionService;
import com.example.rgjalpha1.service.GameService;
import com.example.rgjalpha1.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GameController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class GameControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    GameService gameService;

    @MockBean
    UserService userService;

    @MockBean
    GameConditionService gameConditionService;

    @Test
    @DisplayName("Should add game and game condition and assign to user")
    void shouldAddGameAndGameConditionAndAssignToUser() throws Exception {

        String username = "testUser";
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

        GameDto gameDto = new GameDto();
        gameDto.setGameName("Sonic 666");
        gameDto.setGameYearOfRelease(1993);
        gameDto.setGamePublisher("Sega");
        gameDto.setGameIsOriginal(true);
        gameDto.setSystemName("MegaDrive");

        GameConditionDto gameConditionDto = new GameConditionDto();
        gameConditionDto.setHasManual(true);
        gameConditionDto.setHasCase(true);
        gameConditionDto.setHasScratches(false);
        gameConditionDto.setHasStickers(false);
        gameConditionDto.setHasWriting(false);

        when(gameService.addGame(any(GameDto.class))).thenReturn(gameDto);
        when(gameConditionService.addGameCondition(any(GameConditionDto.class))).thenReturn(gameConditionDto);

        mockMvc.perform(post("/users/{username}/games", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(gameDto.gameName
                        + " added successfully to user " + username)));

    }

    @Test
    @DisplayName("Should return bad request when gameDto has invalid fields")
    void shouldReturnBadRequestWhenGameDtoHasInvalidFields() throws Exception {
        String username = "testUser";
        String requestJson = """
            {
                "gameDto": {
                    "gameName": "",
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

        mockMvc.perform(post("/users/{username}/games", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }


}