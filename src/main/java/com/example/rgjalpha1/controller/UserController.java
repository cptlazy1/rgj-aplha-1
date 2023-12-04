package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.PhotoUploadResponse;
import com.example.rgjalpha1.dto.UserDto;
import com.example.rgjalpha1.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    // GetMapping to get all users
//    @GetMapping("/users")
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        List<UserDto> userDtos = userService.getAllUsers();
//        return ResponseEntity.ok(userDtos);
//    }

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

    // PostMapping to upload a profile photo to a user
    @PostMapping("/users/{username}/upload-pp")

    public PhotoUploadResponse uploadProfilePhoto(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) throws IOException {

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

        return photoUploadResponse;
    }

    // PostMapping to upload a game room photo to a user
    @PostMapping("/users/{username}/upload-grp")
    public PhotoUploadResponse uploadGameRoomPhoto(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) throws IOException {

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

        return photoUploadResponse;
    }


}
