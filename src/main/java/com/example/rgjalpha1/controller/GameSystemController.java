package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.*;
import com.example.rgjalpha1.service.GameConditionService;
import com.example.rgjalpha1.service.GameSystemConditionService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class GameSystemController {

    private final GameSystemService gameSystemService;
    private final UserService userService;
    private final GameSystemConditionService gameSystemConditionService;


    // GetMapping to get all game systems and their conditions
    @GetMapping("/admin/game-systems")
    public ResponseEntity<List<GameSystemAndConditionDto>> getAllGameSystemsAndGameSystemConditions() {
        List<GameSystemDto> gameSystemDtos = gameSystemService.getAllGameSystems();
        List<GameSystemConditionDto> gameSystemConditionDtos = gameSystemConditionService.getAllGameSystemConditions();

        List<GameSystemAndConditionDto> gameSystemAndConditionDtos = new ArrayList<>();
        for (int i = 0; i < gameSystemDtos.size(); i++) {
            GameSystemAndConditionDto gameSystemAndConditionDto = new GameSystemAndConditionDto();
            gameSystemAndConditionDto.setGameSystemDto(gameSystemDtos.get(i));
            gameSystemAndConditionDto.setGameSystemConditionDto(gameSystemConditionDtos.get(i));
            gameSystemAndConditionDtos.add(gameSystemAndConditionDto);
        }

        return ResponseEntity.ok().body(gameSystemAndConditionDtos);
    }


    // GetMapping to get all of a users game systems
    @GetMapping("/users/{username}/game-systems")
    public ResponseEntity<List<GameSystemDto>> getAllGameSystemsByUserName(
            @PathVariable("username") String username) {
        List<GameSystemDto> gameSystemDtos = gameSystemService.getAllGameSystemsOfUser(username);
        return ResponseEntity.ok().body(gameSystemDtos);
    }


    // PostMapping to add game system AND assign it to a user AND assign a game system condition to the game system
    @PostMapping("/users/{username}/game-systems")
    public ResponseEntity<Long> addGameSystem(
            @PathVariable("username") String username,
            @Valid @RequestBody GameSystemAndConditionDto gameSystemAndConditionDto) {
        GameSystemDto gameSystemDto =
                gameSystemService.addGameSystem(gameSystemAndConditionDto.getGameSystemDto());
        GameSystemConditionDto gameSystemConditionDto =
                gameSystemConditionService.addGameSystemCondition(gameSystemAndConditionDto.getGameSystemConditionDto());

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}/game-systems/{id}")
                .buildAndExpand(username, gameSystemDto.getGameSystemID())
                .toUriString());

        userService.assignGameSystemToUser(username, gameSystemDto.getGameSystemID());
        gameSystemConditionService.assignGameSystemCondition(gameSystemConditionDto
                .getGameSystemConditionID(), gameSystemDto.getGameSystemID());

        return ResponseEntity.created(uri)
//                .body(gameSystemDto.getGameSystemName() + " added successfully to user " + username);
//                .body("ID:" + gameSystemDto.getGameSystemID() + " added successfully to user " + username);

                // Return the game system ID instead of the game system name
                .body(gameSystemDto.getGameSystemID());
    }


    // PutMapping to update game system
    @PutMapping("users/{username}/game-systems/{id}")
    public ResponseEntity<GameSystemDto> updateGameSystem(
            @PathVariable("id") Long gameSystemID,
            @Valid @RequestBody GameSystemDto dto)  {
        GameSystemDto gameSystemDto = gameSystemService.updateGameSystem(gameSystemID, dto);
        return ResponseEntity.ok().body(gameSystemDto);
    }

    // DeleteMapping to delete game system
    @DeleteMapping("users/{username}/game-systems/{id}")
    public ResponseEntity<Void> deleteGameSystem(
            @PathVariable("username") String username,
            @PathVariable("id") Long gameSystemID) {
        gameSystemService.deleteGameSystem(username, gameSystemID);
        return ResponseEntity.noContent().build();
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
