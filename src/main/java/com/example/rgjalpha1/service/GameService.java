package com.example.rgjalpha1.service;

import com.example.rgjalpha1.dto.GameAndConditionDto;
import com.example.rgjalpha1.dto.GameConditionDto;
import com.example.rgjalpha1.dto.GameDto;
import com.example.rgjalpha1.exception.BadRequestException;
import com.example.rgjalpha1.exception.RecordNotFoundException;
import com.example.rgjalpha1.model.Game;

import com.example.rgjalpha1.model.GameCondition;
import com.example.rgjalpha1.model.User;
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
    private final UserRepository userRepository;

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

    // Method to get all games of a user
    public List<GameDto> getAllGamesOfUser(String username) {
        List<Game> games = gameRepository.findAllByUserUsername(username);
        List<GameDto> gameDtos = new ArrayList<>();
        for (Game game : games) {
            GameDto gameDto = convertToGameDto(game);
            gameDtos.add(gameDto);
        }
        return gameDtos;
    }

    // Method to get a game, and it's condition by username and gameID
    public GameAndConditionDto getGameByIdAndUserName(String username, Long gameID) {
        Optional<Game> gameOptional = gameRepository.findById(gameID);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (gameOptional.isPresent() && userOptional.isPresent()) {
            Game game = gameOptional.get();
            GameDto gameDto = convertToGameDto(game);

            GameCondition gameCondition = game.getGameCondition();
            GameConditionDto gameConditionDto = convertToGameConditionDto(gameCondition);

            return new GameAndConditionDto(gameDto, gameConditionDto);
        } else {
            throw new RecordNotFoundException("No game record exists for given gameID");
        }
    }


    // Method to update game by username and gameID
    public GameDto updateGame(String username, Long gameID, GameDto gameDto) {

        Optional<Game> gameOptional = gameRepository.findById(gameID);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (gameOptional.isPresent() && userOptional.isPresent()) {
            Game game = gameOptional.get();
            if (gameDto.getGameName() != null) {
                game.setGameName(gameDto.getGameName());
            }
            if (gameDto.getGameYearOfRelease() != null) {
                game.setGameYearOfRelease(gameDto.getGameYearOfRelease());
            }
            if (gameDto.getGamePublisher() != null) {
                game.setGamePublisher(gameDto.getGamePublisher());
            }
            if (gameDto.getGameIsOriginal() != null) {
                game.setGameIsOriginal(gameDto.getGameIsOriginal());
            }
            if (gameDto.getSystemName() != null) {
                game.setSystemName(gameDto.getSystemName());
            }

            Game updatedGame = gameRepository.save(game);
            return convertToGameDto(updatedGame);

        } else {
            throw new RecordNotFoundException("No game record exists for given gameID");
        }
    }


    // Method to delete game by gameID
    // Todo: Add a check to make sure the user is the owner of the game
    // Todo: This method is not deleting the game if it has a user associated with it. Fix this.
    public void deleteGame(Long gameID) {
        Optional<Game> gameOptional = gameRepository.findById(gameID);
        Optional<User> userOptional = userRepository.findByUsername("test");
        if (gameOptional.isPresent() && userOptional.isPresent()) {

            Game game = gameOptional.get();
            User user = game.getUser();
            user.getGames().remove(game);

            gameRepository.deleteById(gameID);
        } else {
            throw new RecordNotFoundException("No game record exists for given gameID or username");
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

//    // Method to download a game photo
//    public byte[] downloadGamePhoto(Long gameID, String username) {
//        Optional<Game> game = gameRepository.findById(gameID);
//        Optional<User> user = userRepository.findByUsername(username);
//
//        try {
//            if (game.isEmpty()) {
//                throw new RecordNotFoundException("No game record exists for given gameID");
//            } else if (user.isEmpty()) {
//                throw new UsernameNotFoundException("No user record exists for given username");
//            } else {
//                Game game1 = game.get();
//                return game1.getGamePhotoData();
//            }
//        } catch (Exception e) {
//            throw new RecordNotFoundException("Issue in downloading the file: ");
//        }
//    }


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

    // Method to convert GameCondition to GameConditionDto with ModelMapper
    public GameConditionDto convertToGameConditionDto(GameCondition gameCondition) {

        ModelMapper modelMapper = new ModelMapper();
        GameConditionDto gameConditionDto = modelMapper.map(gameCondition, GameConditionDto.class);
        return gameConditionDto;
    }

}
