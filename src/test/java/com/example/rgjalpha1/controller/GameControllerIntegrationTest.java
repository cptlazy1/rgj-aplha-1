package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class GameControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager testEntityManager;

    @Test
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
}