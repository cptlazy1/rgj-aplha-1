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


    // Method to null check for the game condition fields
    public Boolean nullCheck(GameConditionDto gameConditionDto) throws BadRequestException {
        if (gameConditionDto.getIsCompleteInBox() == null) {
            throw new BadRequestException("Game condition CIB cannot be null");
        } else if (gameConditionDto.getHasManual() == null) {
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
                gameCondition.setIsCompleteInBox(gameConditionDto.getIsCompleteInBox());
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

    // Method to assign game condition to game
    public void assignGameCondition(Long gameID, Long gameConditionID) {
        Optional<GameCondition> gameConditionOptional = gameConditionRepository.findById(gameConditionID);
        Optional<Game> gameOptional = gameRepository.findById(gameID);
        if (gameConditionOptional.isPresent() && gameOptional.isPresent()) {
            GameCondition gameCondition = gameConditionOptional.get();
            Game game = gameOptional.get();
            gameCondition.setGame(game);
            gameConditionRepository.save(gameCondition);
        } else {
            throw new BadRequestException("Game condition or game with the given ID does not exist");
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
