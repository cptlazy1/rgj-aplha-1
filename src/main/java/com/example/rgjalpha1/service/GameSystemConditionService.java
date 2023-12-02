package com.example.rgjalpha1.service;


import com.example.rgjalpha1.dto.GameSystemConditionDto;
import com.example.rgjalpha1.exception.BadRequestException;
import com.example.rgjalpha1.model.GameSystemCondition;
import com.example.rgjalpha1.repository.GameSystemConditionRepository;
import com.example.rgjalpha1.repository.GameSystemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSystemConditionService {

    private final GameSystemRepository gameSystemRepository;

    private final GameSystemConditionRepository gameSystemConditionRepository;

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
    public GameSystemConditionDto updateGameSystemCondition(Long gameSystemConditionID, GameSystemConditionDto gameSystemConditionDto) {
        if (nullCheck(gameSystemConditionDto)) {
            Optional<GameSystemCondition> existingGameSystemCondition = gameSystemConditionRepository.findById(gameSystemConditionID);
            if (existingGameSystemCondition.isPresent()) {

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

