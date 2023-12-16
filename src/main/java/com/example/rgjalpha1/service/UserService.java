package com.example.rgjalpha1.service;

import com.example.rgjalpha1.dto.UserDto;
import com.example.rgjalpha1.exception.RecordNotFoundException;
import com.example.rgjalpha1.exception.UsernameNotFoundException;
import com.example.rgjalpha1.model.Game;
import com.example.rgjalpha1.model.GameSystem;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.GameRepository;
import com.example.rgjalpha1.repository.GameSystemRepository;
import com.example.rgjalpha1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameSystemRepository gameSystemRepository;


    // Method to get all users - Admin only
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new RecordNotFoundException("No user records exist");
        } else {
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                UserDto userDto = convertToUserDto(user);
                userDtos.add(userDto);
            }
            return userDtos;
        }
    }

    // Method to get user by username
    public UserDto getUserByUserName(String username) {
        UserDto userDto;
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userDto = convertToUserDto(user);

        } else {
            throw new UsernameNotFoundException("No user record exists for given username");
        }
        return userDto;
    }

    // Method to upload a profile photo to a user
    public void uploadProfilePhoto(MultipartFile file, String username) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Optional<User> user = userRepository.findByUsername(username);

        try {
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("No user record exists for given username");
            } else {
                User user1 = user.get();
                user1.setProfilePhotoFileName(fileName);
                user1.setProfilePhotoData(file.getBytes());
                userRepository.save(user1);
            }
        } catch (IOException e) {
            throw new IOException("Issue in uploading the file", e);
        }

    }


    // Method to upload a game room photo to a user
    public void uploadGameRoomPhoto(MultipartFile file, String username) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Optional<User> user = userRepository.findByUsername(username);

        try {
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("No user record exists for given username");
            } else {

                User user1 = user.get();
                user1.setGameRoomPhotoFileName(fileName);
                user1.setGameRoomPhotoData(file.getBytes());
                userRepository.save(user1);
            }
        } catch (IOException e) {
            throw new IOException("Issue in uploading the file", e);
        }

    }


    // Method to delete user profile photo
    public void deleteProfilePhoto(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user record exists for given username");
        } else {
            User user1 = user.get();
            user1.setProfilePhotoFileName(null);
            user1.setProfilePhotoData(null);
            userRepository.save(user1);

        }
    }

    // Method to delete user game room photo
    public void deleteGameRoomPhoto(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user record exists for given username");
        } else {
            User user1 = user.get();
            user1.setGameRoomPhotoFileName(null);
            user1.setGameRoomPhotoData(null);
            userRepository.save(user1);

        }
    }

    // Method to assign game to user
    public void assignGameToUser(String username, Long gameID) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Game> gameOptional = gameRepository.findById(gameID);

        if (userOptional.isPresent() && gameOptional.isPresent()) {
            User user = userOptional.get();
            Game game = gameOptional.get();

            user.getGames().add(game);
            game.setUser(user);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("No user or game record exists for given username or gameID");
        }
    }

    // Method to assign game system to user
    public void assignGameSystemToUser(String username, Long gameSystemID) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<GameSystem> gameSystemOptional = gameSystemRepository.findById(gameSystemID);

        if (userOptional.isPresent() && gameSystemOptional.isPresent()) {
            User user = userOptional.get();
            GameSystem gameSystem = gameSystemOptional.get();

            user.getGameSystems().add(gameSystem);
            gameSystem.setUser(user);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("No user or game record exists for given username or game system ID");
        }
    }

    // Method to convert User to UserDto with ModelMapper
    private UserDto convertToUserDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }


}
