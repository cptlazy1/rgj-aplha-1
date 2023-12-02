package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.UserDto;
import com.example.rgjalpha1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    // GetMapping to get all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    // GetMapping to get user by username
    @GetMapping("/users/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username) {
        UserDto userDto = userService.getUserByUserName(username);
        return ResponseEntity.ok(userDto);
    }

    // Todo: add method to assign user profile photo to user


    // Todo: add method to assign user game room photo to user
}
