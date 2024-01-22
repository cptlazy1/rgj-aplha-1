package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.GameAndConditionDto;
import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.dto.GameDto;
import com.example.rgjalpha1.dto.PhotoUploadResponse;
import com.example.rgjalpha1.exception.UsernameNotFoundException;
import com.example.rgjalpha1.model.Game;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameRepository;
import com.example.rgjalpha1.repository.UserRepository;
import com.example.rgjalpha1.service.GameConditionService;
import com.example.rgjalpha1.service.GameService;
import com.example.rgjalpha1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    private final GameConditionService gameConditionService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;


    // GetMapping to get all games and game conditions
    @GetMapping("/games")
    public ResponseEntity<List<GameAndConditionDto>> getAllGamesAndGameConditions() {

        List<GameDto> gameDtos = gameService.getAllGames();
        List<GameConditionDto> gameConditionDtos = gameConditionService.getAllGameConditions();

        List<GameAndConditionDto> gameAndConditionDtos = new ArrayList<>();
        for (int i = 0; i < gameDtos.size(); i++) {
            GameAndConditionDto gameAndConditionDto = new GameAndConditionDto();
            gameAndConditionDto.setGameDto(gameDtos.get(i));
            gameAndConditionDto.setGameConditionDto(gameConditionDtos.get(i));
            gameAndConditionDtos.add(gameAndConditionDto);
        }

        return ResponseEntity.ok().body(gameAndConditionDtos);
    }


    // PostMapping to add a game AND assign it to a user AND assign a game condition to the game
    @PostMapping("/users/{username}/games")
    public ResponseEntity<Long> addGame(
            @PathVariable("username") String username,
            @Valid @RequestBody GameAndConditionDto gameAndConditionDto) {
        GameDto gameDto = gameService.addGame(gameAndConditionDto.getGameDto());
        GameConditionDto gameConditionDto =
                gameConditionService.addGameCondition(gameAndConditionDto.getGameConditionDto());

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}/games/{id}")
                .buildAndExpand(username, gameDto.getGameID())
                .toUriString());

        userService.assignGameToUser(username, gameDto.getGameID());
        gameConditionService.assignGameCondition(gameConditionDto.getGameConditionID(), gameDto.getGameID());

        return ResponseEntity.created(uri).body(gameDto.getGameID());
    }


    // PostMapping to upload a game photo to a game
    @PostMapping("/users/{username}/games/{gameID}/upload-game-photo")
    public ResponseEntity<PhotoUploadResponse> uploadGamePhoto(
            @PathVariable("username") String username,
            @PathVariable("gameID") Long gameID,
            @RequestParam("file") MultipartFile file) throws IOException {

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/")
                .path(username)
                .path("/games/")
                .path(gameID.toString())
                .path("/download-game-photo/")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        String contentType = file.getContentType();

        gameService.uploadGamePhoto(file, gameID, username);

        PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();
        photoUploadResponse.setFileName(file.getOriginalFilename());
        photoUploadResponse.setFileDownloadUrl(downloadUrl);
        photoUploadResponse.setContentType(contentType);

        return ResponseEntity.ok(photoUploadResponse);
    }


    // GetMapping to download a game photo
    @GetMapping("/users/{username}/games/{gameID}/download-game-photo")
    public ResponseEntity<byte[]> downloadGamePhoto(
            @PathVariable("username") String username,
            @PathVariable("gameID") Long gameID) throws Exception {

        Optional<User> user = userRepository.findByUsername(username);
        Optional<Game> game = gameRepository.findById(gameID);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user record exists for given username");
        } else if (game.isEmpty()) {
            throw new Exception("No game record exists for given gameID");
        } else {
            Game game1 = game.get();

            byte[] photoData = game1.getGamePhotoData();

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; fileName=" + game1.getGamePhotoFileName()).body(photoData);

        }
    }


    // GetMapping to get all of a user's games
    @GetMapping("/users/{username}/games")
    public ResponseEntity<List<GameDto>> getAllUsersGames(
            @PathVariable("username") String username) {
        List<GameDto> gameDtos = gameService.getAllGamesOfUser(username);
        return ResponseEntity.ok().body(gameDtos);
    }

    // GetMapping to get a game, and it's condition by gameID and username
    @GetMapping("/users/{username}/games/{id}")
    public ResponseEntity<GameAndConditionDto> getGameAndCondition(
            @PathVariable("username") String username,
            @PathVariable("id") Long gameID) {
        GameAndConditionDto gameAndConditionDto = gameService.getGameByIdAndUserName(username, gameID);
        return ResponseEntity.ok().body(gameAndConditionDto);
    }


    // Leave this PUT MAPPING here for now

//    // PutMapping to update game by username and gameID
//    @PutMapping("users/{username}/games/{id}")
//    public ResponseEntity<GameDto> updateGame(
//            @PathVariable("username") String username,
//            @PathVariable("id") Long gameID,
//            @RequestBody GameDto dto) {
//        GameDto gameDto = gameService.updateGame(username, gameID, dto);
//        return ResponseEntity.ok().body(gameDto);
//    }


    // DeleteMapping to delete game by gameID
    @DeleteMapping("users/{username}/games/{id}")
    public ResponseEntity<String> deleteGame(
            @PathVariable("username") String username,
            @PathVariable("id") Long gameID) {
        gameService.deleteGame(username, gameID);
        return ResponseEntity.ok().body("Game deleted successfully from user: " + username);
    }

}