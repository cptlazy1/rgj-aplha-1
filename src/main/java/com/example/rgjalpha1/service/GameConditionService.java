package com.example.rgjalpha1.service;

import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.exception.BadRequestException;
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
public class GameConditionService {

    private final GameConditionRepository gameConditionRepository;
    private final GameRepository gameRepository;

    // Todo: Method to null check for the game condition fields
    public Boolean nullCheck(GameConditionDto gameConditionDto) throws BadRequestException {
         if (gameConditionDto.getHasManual() == null) {
            throw new BadRequestException("Game condition Has Manual cannot be null");
        } else if (gameConditionDto.getHasCase() == null) {
            throw new BadRequestException("Game condition Has Case cannot be null");
        }

        else {
            return true;
        }
    }


    // Method to add game condition with null check for the game condition fields
    public GameConditionDto addGameCondition(GameConditionDto gameConditionDto) {

        if (nullCheck(gameConditionDto)) {
            GameCondition gameCondition = convertToGameCondition(gameConditionDto);
            GameCondition savedGameCondition = gameConditionRepository.save(gameCondition);
            return convertToGameConditionDto(savedGameCondition);
        } else {
            throw new BadRequestException("Game condition cannot be null");
        }

    }


    public GameConditionDto updateGameCondition(Long gameConditionID, GameConditionDto gameConditionDto) {
        if (nullCheck(gameConditionDto)) {
            Optional<GameCondition> existingGameCondition = gameConditionRepository.findById(gameConditionID);
            if (existingGameCondition.isPresent()) {

                GameCondition gameCondition = existingGameCondition.get();
                gameCondition.setHasManual(gameConditionDto.getHasManual());
                gameCondition.setHasCase(gameConditionDto.getHasCase());

                GameCondition savedGameCondition = gameConditionRepository.save(gameCondition);
                return convertToGameConditionDto(savedGameCondition);
            } else {
                throw new BadRequestException("Game condition with the given ID does not exist");
            }
        } else {
            throw new BadRequestException("Game condition cannot be null");
        }
    }

    // From AI: It seems like the issue might be related to the GameCondition object being assigned to multiple Game
    // objects. In a typical relational database, if a GameCondition is assigned to a Game, it cannot be assigned to
    // another Game unless the relationship is defined as a many-to-many relationship.  If you want to assign the same
    // GameCondition to multiple Game objects, you need to define a many-to-many relationship between Game and
    // GameCondition. This can be done by adding a Set<Game> field in the GameCondition class and a Set<GameCondition>
    // field in the Game class.

    // There are 8 different game conditions, so I think it would be better to have a one-to-many relationship between
    // Game and GameCondition.  This means that a Game can only have one GameCondition, but a GameCondition can be
    // assigned to multiple Game objects. Find out which relationship is more appropriate for your use case and
    // implement it.

    // Method to assign game condition to game
    public void assignGameCondition(Long gameID, Long gameConditionID) {
        try {
            Optional<GameCondition> gameConditionOptional = gameConditionRepository.findById(gameConditionID);
            Optional<Game> gameOptional = gameRepository.findById(gameID);
            if (gameConditionOptional.isPresent() && gameOptional.isPresent()) {
                Game game = gameOptional.get();
                GameCondition gameCondition = gameConditionOptional.get();

                // Check if the game already has a condition assigned to it
                if (game.getGameCondition() != null) {
                    throw new BadRequestException("The game already has a condition assigned to it");
                }

                // If not, assign the new condition to the game
                game.setGameCondition(gameCondition);

                gameConditionRepository.save(gameCondition);
            } else {
                throw new BadRequestException("Game condition or game with the given ID does not exist");
            }
        } catch (BadRequestException e) {
            // Log the exception and rethrow it
            System.err.println(e.getMessage());
            throw e;
        }
    }


    // Method to convert GameConditionDto to GameCondition
    public GameCondition convertToGameCondition(GameConditionDto gameConditionDto) {
        ModelMapper modelMapper = new ModelMapper();
        GameCondition gameCondition = modelMapper.map(gameConditionDto, GameCondition.class);
        return gameCondition;
    }

    // Method to convert GameCondition to GameConditionDto

    public GameConditionDto convertToGameConditionDto(GameCondition gameCondition) {
        ModelMapper modelMapper = new ModelMapper();
        GameConditionDto gameConditionDto = modelMapper.map(gameCondition, GameConditionDto.class);
        return gameConditionDto;
    }


}
