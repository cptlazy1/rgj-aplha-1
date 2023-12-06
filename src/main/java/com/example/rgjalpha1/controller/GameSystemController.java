package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.GameSystemDto;
import com.example.rgjalpha1.dto.PhotoUploadResponse;
import com.example.rgjalpha1.service.GameSystemService;
import com.example.rgjalpha1.service.UserService;
import jakarta.validation.Valid;
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
public class GameSystemController {

    private final GameSystemService gameSystemService;
    private final UserService userService;

    // PostMapping to add game system
//    @PostMapping("/game-systems")
//    public ResponseEntity<GameSystemDto> addGameSystem(@Valid @RequestBody GameSystemDto dto) {
//        GameSystemDto gameSystemDto = gameSystemService.addGameSystem(dto);
//
//        URI uri = URI.create(ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/game-systems/{id}")
//                .buildAndExpand(gameSystemDto.getGameSystemID())
//                .toUriString());
//
//        return ResponseEntity.created(uri).body(gameSystemDto);
//    }

    // GetMapping to get all game systems
    @GetMapping("/admin/game-systems")
    public ResponseEntity<List<GameSystemDto>> getAllGameSystems() {
        List<GameSystemDto> gameSystemDtos = gameSystemService.getAllGameSystems();
        return ResponseEntity.ok().body(gameSystemDtos);
    }

    // GetMapping to get all of a users game systems
    @GetMapping("/users/{username}/game-systems")
    public ResponseEntity<List<GameSystemDto>> getAllGameSystemsByUserName(
            @PathVariable("username") String username) {
        List<GameSystemDto> gameSystemDtos = gameSystemService.getAllGameSystemsOfUser(username);
        return ResponseEntity.ok().body(gameSystemDtos);
    }


    // PostMapping to add game system AND assign it to a user
    @PostMapping("/users/{username}/game-systems")
    public ResponseEntity<GameSystemDto> addGameSystem(@PathVariable("username") String username, @RequestBody GameSystemDto dto) {
        GameSystemDto gameSystemDto = gameSystemService.addGameSystem(dto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}/game-systems/{id}")
                .buildAndExpand(username, gameSystemDto.getGameSystemID())
                .toUriString());

        userService.assignGameSystemToUser(username, gameSystemDto.getGameSystemID());

        return ResponseEntity.created(uri).body(gameSystemDto);
    }

    // PutMapping to update game system
    @PutMapping("/game-systems/{id}")
    public ResponseEntity<GameSystemDto> updateGameSystem(@PathVariable("id") Long gameSystemID, @Valid @RequestBody GameSystemDto dto)  {
        GameSystemDto gameSystemDto = gameSystemService.updateGameSystem(gameSystemID, dto);
        return ResponseEntity.ok().body(gameSystemDto);
    }

    // DeleteMapping to delete game system
    @DeleteMapping("/game-systems/{id}")
    public ResponseEntity<Void> deleteGameSystem(@PathVariable("id") Long gameSystemID) {
        gameSystemService.deleteGameSystem(gameSystemID);
        return ResponseEntity.noContent().build();
    }

    // PutMapping to assign game condition to game system
    @PutMapping("/game-systems/{gameSystemID}/game-system-conditions/{gameSystemConditionID}")
    public ResponseEntity<String> assignGameSystemCondition(@PathVariable("gameSystemID") Long gameSystemID, @PathVariable("gameSystemConditionID") Long gameSystemConditionID)  {
        gameSystemService.assignGameSystemCondition(gameSystemID, gameSystemConditionID);
        return ResponseEntity.ok().body("Game system condition assigned successfully to game system");
    }

    // PostMapping to upload a game system photo to a game system
    @PostMapping("/users/{username}/game-systems/{gameSystemID}/upload-game-system-photo")
    public PhotoUploadResponse uploadGameSystemPhoto(
            @PathVariable("username") String username,
            @PathVariable("gameSystemID") Long gameSystemID,
            @RequestParam("file") MultipartFile file) throws IOException {

            String downloadUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("users/{username}/game-systems/")
                    .path(gameSystemID.toString())
                    .path("/download-game-system-photo")
                    .path(Objects.requireNonNull(file.getOriginalFilename()))
                    .toUriString();

            String contentType = file.getContentType();

            gameSystemService.uploadGameSystemPhoto(file, gameSystemID, username);

            PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();
            photoUploadResponse.setFileName(file.getOriginalFilename());
            photoUploadResponse.setFileDownloadUrl(downloadUrl);
            photoUploadResponse.setContentType(contentType);

            return photoUploadResponse;
    }

}
