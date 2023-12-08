package com.example.rgjalpha1.service;


import com.example.rgjalpha1.dto.GameSystemConditionDto;
import com.example.rgjalpha1.exception.BadRequestException;
import com.example.rgjalpha1.model.GameSystem;
import com.example.rgjalpha1.model.GameSystemCondition;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameSystemConditionRepository;
import com.example.rgjalpha1.repository.GameSystemRepository;
import com.example.rgjalpha1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSystemConditionService {

    private final GameSystemConditionRepository gameSystemConditionRepository;
    private final GameSystemRepository gameSystemRepository;
    private final UserRepository userRepository;

    // Method to null check for the game system condition fields
    public Boolean nullCheck(GameSystemConditionDto gameSystemConditionDto) throws BadRequestException {
        if (gameSystemConditionDto.getHasBox() == null) {
            throw new BadRequestException("Game system condition Has Box cannot be null");
        } else if (gameSystemConditionDto.getHasCables() == null) {
            throw new BadRequestException("Game system condition Has Cables cannot be null");
        } else if (gameSystemConditionDto.getIsModified() == null) {
            throw new BadRequestException("Game system condition Is Modified cannot be null");
        }
        else {
            return true;
        }
    }

    // Method to add game system condition with null check for the game system condition ID
    public GameSystemConditionDto addGameSystemCondition(GameSystemConditionDto gameSystemConditionDto) {

        if (nullCheck(gameSystemConditionDto)) {
            GameSystemCondition gameSystemCondition = convertToGameSystemCondition(gameSystemConditionDto);
            GameSystemCondition savedGameSystemCondition = gameSystemConditionRepository.save(gameSystemCondition);
            return convertToGameSystemConditionDto(savedGameSystemCondition);
        } else {
            throw new BadRequestException("Game system condition cannot be null");
        }

    }


    // Method to update game system condition by gameSystemConditionID
    public GameSystemConditionDto updateGameSystemCondition(
            String username,
            Long gameSystemConditionID,
            GameSystemConditionDto gameSystemConditionDto) {
        if (nullCheck(gameSystemConditionDto)) {
            Optional<GameSystemCondition> existingGameSystemCondition = gameSystemConditionRepository.findById(gameSystemConditionID);
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingGameSystemCondition.isPresent() && existingUser.isPresent()) {

                GameSystemCondition gameSystemCondition = existingGameSystemCondition.get();
                gameSystemCondition.setHasBox(gameSystemConditionDto.getHasBox());
                gameSystemCondition.setHasCables(gameSystemConditionDto.getHasCables());
                gameSystemCondition.setIsModified(gameSystemConditionDto.getIsModified());

                GameSystemCondition updatedGameSystemCondition = gameSystemConditionRepository.save(gameSystemCondition);
                return convertToGameSystemConditionDto(updatedGameSystemCondition);
            } else {
                throw new BadRequestException("No game system condition record exists for given gameSystemConditionID");
            }
        }
        else {
            throw new BadRequestException("Game system condition cannot be null");
        }
    }

    // Method to assign game system condition to game system
    public void assignGameSystemCondition(Long gameSystemID, Long gameSystemConditionID) {

        Optional<GameSystemCondition> gameSystemConditionOptional = gameSystemConditionRepository.findById(gameSystemConditionID);
        Optional<GameSystem> gameSystemOptional = gameSystemRepository.findById(gameSystemID);

        if (gameSystemConditionOptional.isPresent() && gameSystemOptional.isPresent()) {
            GameSystemCondition gameSystemCondition = gameSystemConditionOptional.get();
            GameSystem gameSystem = gameSystemOptional.get();
            gameSystem.setGameSystemCondition(gameSystemCondition);
            gameSystemRepository.save(gameSystem);
        } else {
            throw new BadRequestException("Game system condition or game system with the given ID does not exist");
        }
    }

    // Method to convert GameSystemConditionDto to GameSystemCondition with model mapper
    public GameSystemCondition convertToGameSystemCondition(GameSystemConditionDto gameSystemConditionDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(gameSystemConditionDto, GameSystemCondition.class);
    }

    // Method to convert GameSystemCondition to GameSystemConditionDto with model mapper
    public GameSystemConditionDto convertToGameSystemConditionDto(GameSystemCondition gameSystemCondition) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(gameSystemCondition, GameSystemConditionDto.class);
    }

}

