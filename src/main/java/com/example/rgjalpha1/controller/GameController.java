package com.example.rgjalpha1.controller;


import com.example.rgjalpha1.dto.GameDto;
import com.example.rgjalpha1.dto.PhotoUploadResponse;
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

    // PostMapping to add game
//    @PostMapping("/users/{username}/games")
//    public ResponseEntity<GameDto> addGame(@RequestBody GameDto dto) {
//        GameDto gameDto = gameService.addGame(dto);
//
//        URI uri = URI.create(ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/games/{id}")
//                .buildAndExpand(gameDto.getGameID())
//                .toUriString());
//
//        return ResponseEntity.created(uri).body(gameDto);
//    }

    // PostMapping to add a game AND assign it to a user
    @PostMapping("/users/{username}/games")
    public ResponseEntity<GameDto> addGame(@PathVariable("username") String username, @RequestBody GameDto dto) {
        GameDto gameDto = gameService.addGame(dto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}/games/{id}")
                .buildAndExpand(username, gameDto.getGameID())
                .toUriString());

        userService.assignGameToUser(username, gameDto.getGameID());

        return ResponseEntity.created(uri).body(gameDto);
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

    // GetMapping to get all games
    @GetMapping("/admin/games")
    public ResponseEntity<List<GameDto>> getAllGames() {
        List<GameDto> gameDtos = gameService.getAllGames();
        return ResponseEntity.ok().body(gameDtos);
    }


    // PutMapping to update game by gameID
    @PutMapping("/games/{id}")
    public ResponseEntity<GameDto> updateGame(@PathVariable("id") Long gameID, @RequestBody GameDto dto)  {
        GameDto gameDto = gameService.updateGame(gameID, dto);
        return ResponseEntity.ok().body(gameDto);
    }

    // DeleteMapping to delete game by gameID
    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long gameID) {
        gameService.deleteGame(gameID);
        return ResponseEntity.noContent().build();
    }

    // Assign a game condition to a game
    @PutMapping("/games/{gameID}/game-conditions/{gameConditionID}")
    public ResponseEntity<String> assignGameCondition(@PathVariable("gameID") Long gameID, @PathVariable("gameConditionID") Long gameConditionID)  {
        gameService.assignGameCondition(gameID, gameConditionID);
        return ResponseEntity.ok().body("Game condition assigned successfully to game");
    }

}