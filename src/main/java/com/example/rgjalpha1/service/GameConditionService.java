package com.example.rgjalpha1.service;

import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.exception.BadRequestException;
import com.example.rgjalpha1.model.Game;
import com.example.rgjalpha1.model.GameCondition;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameConditionRepository;
import com.example.rgjalpha1.repository.GameRepository;
import com.example.rgjalpha1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameConditionService {

    private final GameConditionRepository gameConditionRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public Boolean nullCheck(GameConditionDto gameConditionDto) throws BadRequestException {
         if (gameConditionDto.getHasManual() == null) {
            throw new BadRequestException("Game condition Has Manual cannot be null");
        } else if (gameConditionDto.getHasCase() == null) {
            throw new BadRequestException("Game condition Has Case cannot be null");
        } else if (gameConditionDto.getHasStickers() == null) {
            throw new BadRequestException("Game condition Has Stickers cannot be null");
        } else if (gameConditionDto.getHasScratches() == null) {
            throw new BadRequestException("Game condition Has Scratches cannot be null");
        } else if (gameConditionDto.getHasWriting() == null) {
            throw new BadRequestException("Game condition Has Writing cannot be null");
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


    public GameConditionDto updateGameCondition(
            String username,
            Long gameConditionID,
            GameConditionDto gameConditionDto) {
        if (nullCheck(gameConditionDto)) {
            Optional<GameCondition> existingGameCondition = gameConditionRepository.findById(gameConditionID);
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingGameCondition.isPresent() && existingUser.isPresent()) {

                GameCondition gameCondition = existingGameCondition.get();
                gameCondition.setHasManual(gameConditionDto.getHasManual());
                gameCondition.setHasCase(gameConditionDto.getHasCase());
                gameCondition.setHasStickers(gameConditionDto.getHasStickers());
                gameCondition.setHasScratches(gameConditionDto.getHasScratches());
                gameCondition.setHasWriting(gameConditionDto.getHasWriting());

                GameCondition savedGameCondition = gameConditionRepository.save(gameCondition);
                return convertToGameConditionDto(savedGameCondition);
            } else {
                throw new BadRequestException("Game condition with the given ID does not exist");
            }
        } else {
            throw new BadRequestException("Game condition cannot be null");
        }
    }


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

    // Method to get all game conditions
    public List<GameConditionDto> getAllGameConditions() {
        List<GameCondition> gameConditions = gameConditionRepository.findAll();
        List<GameConditionDto> gameConditionDtos = new ArrayList<>();
        for (GameCondition gameCondition : gameConditions) {
            GameConditionDto gameConditionDto = convertToGameConditionDto(gameCondition);
            gameConditionDtos.add(gameConditionDto);
        }
        return gameConditionDtos;
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
