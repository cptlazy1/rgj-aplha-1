package com.example.rgjalpha1.service;


import com.example.rgjalpha1.dto.GameDto;
import com.example.rgjalpha1.exception.BadRequestException;
import com.example.rgjalpha1.exception.RecordNotFoundException;
import com.example.rgjalpha1.model.Game;
import com.example.rgjalpha1.model.GameCondition;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameConditionRepository;
import com.example.rgjalpha1.repository.GameRepository;
import com.example.rgjalpha1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameConditionRepository gameConditionRepository;
    private final UserRepository userRepository;

    // Todo: add validation to every method

    // Method to add game with null check for the game name
    public GameDto addGame(GameDto gameDto) throws BadRequestException {
        if (gameDto.getGameName() == null) {
            throw new BadRequestException("Game name cannot be null");
        } else {
            Game game = convertToGame(gameDto);
            Game savedGame = gameRepository.save(game);
            return convertToGameDto(savedGame);
        }

    }

    // Method to get all games
    public List<GameDto> getAllGames() {
        List<Game> games = gameRepository.findAll();
        List<GameDto> gameDtos = new ArrayList<>();
        for (Game game : games) {
            GameDto gameDto = convertToGameDto(game);
            gameDtos.add(gameDto);
        }
        return gameDtos;
    }

    // Method to update game by gameID
    public GameDto updateGame(Long gameID, GameDto gameDto) {

        Optional<Game> gameOptional = gameRepository.findById(gameID);

        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();

            // Manual null check for each field
            if (gameDto.getGameName() != null) {
                game.setGameName(gameDto.getGameName());
            }
            if (gameDto.getGameReview() != null) {
                game.setGameReview(gameDto.getGameReview());
            }
            if (gameDto.getGameRating() != null) {
                game.setGameRating(gameDto.getGameRating());
            }

            Game updatedGame = gameRepository.save(game);
            return convertToGameDto(updatedGame);
        } else {
            throw new RecordNotFoundException("No game record exists for given gameID");
        }

    }

    // Method to delete game by gameID
    public void deleteGame(Long gameID) {
        Optional<Game> gameOptional = gameRepository.findById(gameID);
        if (gameOptional.isPresent()) {
            gameRepository.deleteById(gameID);
        } else {
            throw new RecordNotFoundException("No game record exists for given gameID");
        }
    }

    // Method to assign game condition to game
    public void assignGameCondition(Long gameID, Long gameConditionID) {

        Optional<Game> gameOptional = gameRepository.findById(gameID);
        Optional<GameCondition> gameConditionOptional = gameConditionRepository.findById(gameConditionID);

        if (gameOptional.isPresent() && gameConditionOptional.isPresent()) {

            Game game = gameOptional.get();
            GameCondition gameCondition = gameConditionOptional.get();

            game.setGameCondition(gameCondition);
            gameRepository.save(game);

        } else {
            throw new RecordNotFoundException("No game record exists for given gameID");
        }
    }


    // Method to upload a game photo to a game
    public void uploadGamePhoto(MultipartFile file, Long gameID, String username) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Optional<Game> game = gameRepository.findById(gameID);
        Optional<User> user = userRepository.findByUsername(username);

        try {
            if (game.isEmpty()) {
                throw new RecordNotFoundException("No game record exists for given gameID");
            } else if (user.isEmpty()) {
                throw new UsernameNotFoundException("No user record exists for given username");
            } else {
                Game game1 = game.get();
                game1.setGamePhotoFileName(fileName);
                game1.setGamePhotoData(file.getBytes());
                gameRepository.save(game1);

            }
        } catch (IOException e) {
            throw new IOException("Issue in uploading the file: " + fileName, e);
        }

    }


    // Method to convert GameDto to Game with ModelMapper
    public Game convertToGame(GameDto gameDto) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(gameDto, Game.class);
    }

    // Method to convert Game to GameDto with ModelMapper
    public GameDto convertToGameDto(Game game) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(game, GameDto.class);
    }

}
