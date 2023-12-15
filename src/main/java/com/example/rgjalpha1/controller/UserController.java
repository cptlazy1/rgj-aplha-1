package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.PhotoUploadResponse;
import com.example.rgjalpha1.dto.UserDto;
import com.example.rgjalpha1.exception.UsernameNotFoundException;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.UserRepository;
import com.example.rgjalpha1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;

    // GetMapping to get all users
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    // GetMapping to get user by username
    @GetMapping("/users/{username}")
    public ResponseEntity<UserDto> getUserByUsername(
            @PathVariable("username") String username) {
        UserDto userDto = userService.getUserByUserName(username);
        return ResponseEntity.ok(userDto);
    }


    @PostMapping("/users/{username}/upload-pp")
    public ResponseEntity<PhotoUploadResponse> uploadProfilePhoto(
            @PathVariable("username") String username,
            @RequestParam("file") MultipartFile file) throws IOException {

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/")
                .path(username)
                .path("/download-pp/")
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
    public ResponseEntity<PhotoUploadResponse> uploadGameRoomPhoto(
            @PathVariable("username") String username,
            @RequestParam("file") MultipartFile file) throws IOException {

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/")
                .path(username)
                .path("/download-grp/")
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

    // GetMapping to download a profile photo by username
    @GetMapping("/users/{username}/download-pp/{fileName}")
    public ResponseEntity<byte[]> downloadProfilePhoto(@PathVariable String username, @PathVariable String fileName) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user record exists for given username");
        } else {
            User user1 = user.get();
            if (!user1.getProfilePhotoFileName().equals(fileName)) {
                throw new Exception("File not found for the given user");
            }

            byte[] photoData = user1.getProfilePhotoData();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;fileName=" + user1.getProfilePhotoFileName()).body(photoData);
        }
    }

    // GetMapping to download a game room photo by username
    @GetMapping("/users/{username}/download-grp/{fileName}")
    public ResponseEntity<byte[]> downloadGameRoomPhoto(@PathVariable String username, @PathVariable String fileName) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user record exists for given username");
        } else {
            User user1 = user.get();
            if (!user1.getGameRoomPhotoFileName().equals(fileName)) {
                throw new Exception("File not found for the given user");
            }

            byte[] photoData = user1.getGameRoomPhotoData();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;fileName=" + user1.getGameRoomPhotoFileName()).body(photoData);
        }
    }


    // DeleteMapping to delete user profile photo by username
    @DeleteMapping("/users/{username}/delete-pp")
    public ResponseEntity<String> deleteProfilePhoto(
            @PathVariable("username") String username) {
        userService.deleteProfilePhoto(username);
        return ResponseEntity.ok(username + "'s profile photo has been deleted ");
    }

    // DeleteMapping to delete user game room photo by username
    @DeleteMapping("/users/{username}/delete-grp")
    public ResponseEntity<String> deleteGameRoomPhoto(
            @PathVariable("username") String username) {
        userService.deleteGameRoomPhoto(username);
        return ResponseEntity.ok(username + "'s game room photo has been deleted ");
    }

}
