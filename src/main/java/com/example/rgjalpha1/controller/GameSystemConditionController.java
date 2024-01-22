// Leave this file here for now

//package com.example.rgjalpha1.controller;
//
//import com.example.rgjalpha1.dto.GameSystemConditionDto;
//import com.example.rgjalpha1.model.GameSystemCondition;
//import com.example.rgjalpha1.repository.GameSystemConditionRepository;
//import com.example.rgjalpha1.service.GameSystemConditionService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.Optional;
//
//@RestController
//@RequiredArgsConstructor
//public class GameSystemConditionController {
//
//    private final GameSystemConditionService gameSystemConditionService;
//    private final GameSystemConditionRepository gameSystemConditionRepository;
//
//
//    // PutMapping to update game system condition by gameSystemConditionID
//    @PutMapping("/users/{username}/game-system-conditions/{id}")
//    public ResponseEntity<GameSystemConditionDto> updateGameSystemCondition(
//            @PathVariable("username") String username,
//            @PathVariable("id") Long gameSystemConditionID,
//            @Valid @RequestBody GameSystemConditionDto dto) {
//        Optional<GameSystemCondition> existingGameSystemCondition =
//                gameSystemConditionRepository.findById(gameSystemConditionID);
//        if (existingGameSystemCondition.isPresent()) {
//            GameSystemConditionDto gameSystemConditionDto =
//                    gameSystemConditionService.updateGameSystemCondition(username, gameSystemConditionID, dto);
//
//            URI uri = URI.create(ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/users/{username}/game-system-conditions/{id}")
//                    .buildAndExpand(username, gameSystemConditionDto.getGameSystemConditionID())
//                    .toUriString());
//
//            return ResponseEntity.created(uri).body(gameSystemConditionDto);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//}
