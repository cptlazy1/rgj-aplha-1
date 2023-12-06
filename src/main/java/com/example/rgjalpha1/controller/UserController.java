package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.PhotoUploadResponse;
import com.example.rgjalpha1.dto.UserDto;
import com.example.rgjalpha1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    // GetMapping to get all users
    @GetMapping("/admin")
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


    @PostMapping("/users/{username}/upload-pp")
    public ResponseEntity<PhotoUploadResponse> uploadProfilePhoto(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) throws IOException {

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/")
                .path(username)
                .path("/download-pp")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        String contentType = file.getContentType();

        userService.uploadProfilePhoto(file, username);

        PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();
        photoUploadResponse.setFileName(file.getOriginalFilename());
        photoUploadResponse.setFileDownloadUrl(downloadUrl);
        photoUploadResponse.setContentType(contentType);

        return ResponseEntity.ok(photoUploadResponse);
    }


    // PostMapping to upload a game room photo to a user
    @PostMapping("/users/{username}/upload-grp")
    public ResponseEntity<PhotoUploadResponse> uploadGameRoomPhoto(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) throws IOException {

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/")
                .path(username)
                .path("/download-grp")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        String contentType = file.getContentType();

        userService.uploadGameRoomPhoto(file, username);

        PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();
        photoUploadResponse.setFileName(file.getOriginalFilename());
        photoUploadResponse.setFileDownloadUrl(downloadUrl);
        photoUploadResponse.setContentType(contentType);

        return ResponseEntity.ok(photoUploadResponse);
    }

    // DeleteMapping to delete user profile photo by username
    @DeleteMapping("/users/{username}/delete-pp")
    public ResponseEntity<String> deleteProfilePhoto(@PathVariable("username") String username) {
        userService.deleteProfilePhoto(username);
        return ResponseEntity.ok(username + "'s profile photo has been deleted ");
    }

    // DeleteMapping to delete user game room photo by username
    @DeleteMapping("/users/{username}/delete-grp")
    public ResponseEntity<String> deleteGameRoomPhoto(@PathVariable("username") String username) {
        userService.deleteGameRoomPhoto(username);
        return ResponseEntity.ok(username + "'s game room photo has been deleted ");
    }

    // PutMapping to assign game to user
    @PutMapping("/users/{username}/games/{gameID}")
    public ResponseEntity<Object> assignGameToUser(@PathVariable("username") String username, @PathVariable("gameID") Long gameID) {
        userService.assignGameToUser(username, gameID);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{userID}/games/{gameID}")
                .buildAndExpand(username, gameID)
                .toUriString());

        return ResponseEntity.noContent().location(uri).build();
    }

    // PutMapping to assign game system to user

    // GetMapping to download a game room photo from a user (is it necessary?)


    // GetMapping to download a profile photo from a user (is it necessary?)

}
