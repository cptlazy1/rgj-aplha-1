package com.example.rgjalpha1.controller;


import com.example.rgjalpha1.dto.GameAndConditionDto;
import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.dto.GameDto;
import com.example.rgjalpha1.dto.PhotoUploadResponse;
import com.example.rgjalpha1.service.GameConditionService;
import com.example.rgjalpha1.service.GameService;
import com.example.rgjalpha1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    private final GameConditionService gameConditionService;


    // GetMapping to get all games
    @GetMapping("/admin/games")
    public ResponseEntity<List<GameDto>> getAllGames() {
        List<GameDto> gameDtos = gameService.getAllGames();
        return ResponseEntity.ok().body(gameDtos);
    }


    // PostMapping to add a game AND assign it to a user AND assign a game condition to the game
    @PostMapping("/users/{username}/games")
    public ResponseEntity<String> addGame(
            @PathVariable("username") String username,
            @RequestBody GameAndConditionDto gameAndConditionDto) {
        GameDto gameDto = gameService.addGame(gameAndConditionDto.getGameDto());
        GameConditionDto gameConditionDto = gameConditionService.addGameCondition(gameAndConditionDto.getGameConditionDto());

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}/games/{id}")
                .buildAndExpand(username, gameDto.getGameID())
                .toUriString());

        userService.assignGameToUser(username, gameDto.getGameID());
        gameConditionService.assignGameCondition(gameConditionDto.getGameConditionID(), gameDto.getGameID());

        return ResponseEntity.created(uri).body(gameDto.getGameName() + " added successfully to user " + username);
    }


    // PostMapping to upload a game photo to a game
    @PostMapping("/users/{username}/games/{gameID}/upload-game-photo")
    public PhotoUploadResponse uploadGamePhoto(
            @PathVariable("username") String username,
            @PathVariable("gameID") Long gameID,
            @RequestParam("file") MultipartFile file) throws IOException {

            String downloadUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("users/{username}/games/")
                    .path(gameID.toString())
                    .path("/download-game-photo")
                    .path(Objects.requireNonNull(file.getOriginalFilename()))
                    .toUriString();

            String contentType = file.getContentType();

            gameService.uploadGamePhoto(file, gameID, username);

            PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();
            photoUploadResponse.setFileName(file.getOriginalFilename());
            photoUploadResponse.setFileDownloadUrl(downloadUrl);
            photoUploadResponse.setContentType(contentType);

            return photoUploadResponse;
    }


    // GetMapping to get all of a users games
    @GetMapping("/users/{username}/games")
    public ResponseEntity<List<GameDto>> getAllUsersGames(
            @PathVariable("username") String username) {
        List<GameDto> gameDtos = gameService.getAllGamesOfUser(username);
        return ResponseEntity.ok().body(gameDtos);
    }


    // PutMapping to update game by gameID
    @PutMapping("/games/{id}")
    public ResponseEntity<GameDto> updateGame(
            @PathVariable("id") Long gameID,
            @RequestBody GameDto dto)  {
        GameDto gameDto = gameService.updateGame(gameID, dto);
        return ResponseEntity.ok().body(gameDto);
    }


    // Assign a game condition to a game
    @PutMapping("/games/{gameID}/game-conditions/{gameConditionID}")
    public ResponseEntity<String> assignGameCondition(
            @PathVariable("gameID") Long gameID,
            @PathVariable("gameConditionID") Long gameConditionID)  {
        gameService.assignGameCondition(gameID, gameConditionID);
        return ResponseEntity.ok().body("Game condition assigned successfully to game");
    }


    // DeleteMapping to delete game by gameID
    @DeleteMapping("users/{username}/games/{id}")
    public ResponseEntity<Void> deleteGame(
            @PathVariable("username") String username,
            @PathVariable("id") Long gameID) {
        gameService.deleteGame(gameID);
        return ResponseEntity.noContent().build();
    }

}