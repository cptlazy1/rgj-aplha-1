package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.model.GameCondition;
import com.example.rgjalpha1.repository.GameConditionRepository;
import com.example.rgjalpha1.service.GameConditionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class GameConditionController {

    private final GameConditionService gameConditionService;
    private final GameConditionRepository gameConditionRepository;

    // PutMapping to update game condition by gameConditionID
    @PutMapping("/users/{username}/game-conditions/{id}")
    public ResponseEntity<GameConditionDto> updateGameCondition(
            @PathVariable("username") String username,
            @PathVariable("id") Long gameConditionID,
            @Valid @RequestBody GameConditionDto dto) {

        Optional<GameCondition> existingGameCondition = gameConditionRepository.findById(gameConditionID);

        if (existingGameCondition.isPresent()) {
            GameConditionDto gameConditionDto =
                    gameConditionService.updateGameCondition(username, gameConditionID, dto);

            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/users/{username}/game-conditions/{id}")
                    .buildAndExpand(username, gameConditionDto.getGameConditionID())
                    .toUriString());

            return ResponseEntity.created(uri).body(gameConditionDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
