package com.example.rgjalpha1.service;


import com.example.rgjalpha1.dto.GameDto;
import com.example.rgjalpha1.exception.BadRequestException;
import com.example.rgjalpha1.exception.RecordNotFoundException;
import com.example.rgjalpha1.model.Game;
import com.example.rgjalpha1.model.GameCondition;
import com.example.rgjalpha1.repository.GameConditionRepository;
import com.example.rgjalpha1.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameConditionRepository gameConditionRepository;


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
