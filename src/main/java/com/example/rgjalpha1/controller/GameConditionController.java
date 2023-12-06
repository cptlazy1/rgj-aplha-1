package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.model.GameCondition;
import com.example.rgjalpha1.repository.GameConditionRepository;
import com.example.rgjalpha1.service.GameConditionService;
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

    // PostMapping to add game condition
    @PostMapping("/game-conditions")
    public ResponseEntity<GameConditionDto> addGameCondition(@RequestBody GameConditionDto dto) {
        GameConditionDto gameConditionDto = gameConditionService.addGameCondition(dto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/game-conditions/{id}")
                .buildAndExpand(gameConditionDto.getGameConditionID())
                .toUriString());

        return ResponseEntity.created(uri).body(gameConditionDto);
    }

    // PutMapping to update game condition by gameConditionID
    @PutMapping("/game-conditions/{id}")
    public ResponseEntity<GameConditionDto> updateGameCondition(@PathVariable("id") Long gameConditionID, @RequestBody GameConditionDto dto) {
        Optional<GameCondition> existingGameCondition = gameConditionRepository.findById(gameConditionID);
        if (existingGameCondition.isPresent()) {
            GameConditionDto gameConditionDto = gameConditionService.updateGameCondition(gameConditionID, dto);
            URI uri = URI.create(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/game-conditions/{id}")
                    .buildAndExpand(gameConditionDto.getGameConditionID())
                    .toUriString());

            return ResponseEntity.created(uri).body(gameConditionDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // PutMapping to assign game condition to game
//    @PutMapping("users/{username}/games/{gameID}/game-conditions/{gameConditionID}")
//    public ResponseEntity<String> assignGameCondition(
//            @PathVariable("gameConditionID") Long gameConditionID,
//            @PathVariable("gameID") Long gameID)  {
//        gameConditionService.assignGameCondition(gameConditionID, gameID);
//        return ResponseEntity.ok().body("Game condition with ID: " + gameConditionID +
//                " has been assigned successfully to game with ID: " + gameID);
//    }

    @PostMapping("users/{username}/games/{gameID}/game-conditions/{gameConditionID}")
    public ResponseEntity<String> assignGameCondition(
            @PathVariable("gameConditionID") Long gameConditionID,
            @PathVariable("gameID") Long gameID)  {
        gameConditionService.assignGameCondition(gameConditionID, gameID);
        return ResponseEntity.ok().body("Game condition with ID: " + gameConditionID +
                " has been assigned successfully to game with ID: " + gameID);
    }

}
