package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.EmailChangeRequest;
import com.example.rgjalpha1.dto.PasswordChangeRequest;
import com.example.rgjalpha1.dto.PhotoUploadResponse;
import com.example.rgjalpha1.dto.UserDto;
import com.example.rgjalpha1.exception.UsernameNotFoundException;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.UserRepository;
import com.example.rgjalpha1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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


    // PostMapping to upload a profile photo to a user
    @PostMapping("/users/{username}/upload-pp")
    public ResponseEntity<PhotoUploadResponse> uploadProfilePhoto(
            @PathVariable("username") String username,
            @RequestParam("file") MultipartFile file) throws IOException {

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/")
                .path(username)
                .path("/download-pp")
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
                .path("/download-grp")
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
    @GetMapping("/users/{username}/download-pp")
    public ResponseEntity<byte[]> downloadProfilePhoto(
            @PathVariable String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user record exists for given username");
        } else {
            User user1 = user.get();

            byte[] photoData = user1.getProfilePhotoData();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; fileName=" + user1.getProfilePhotoFileName()).body(photoData);
        }
    }

    // GetMapping to download a game room photo by username
    @GetMapping("/users/{username}/download-grp")
    public ResponseEntity<byte[]> downloadGameRoomPhoto(
            @PathVariable String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user record exists for given username");
        } else {
            User user1 = user.get();

            byte[] photoData = user1.getGameRoomPhotoData();

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;fileName=" + user1.getGameRoomPhotoFileName()).body(photoData);
        }
    }

    // Leave these two DELETE MAPPING endpoints for later use

//    // DeleteMapping to delete user profile photo by username
//    @DeleteMapping("/users/{username}/delete-pp")
//    public ResponseEntity<String> deleteProfilePhoto(
//            @PathVariable("username") String username) {
//        userService.deleteProfilePhoto(username);
//        return ResponseEntity.ok(username + "'s profile photo has been deleted ");
//    }
//
//    // DeleteMapping to delete user game room photo by username
//    @DeleteMapping("/users/{username}/delete-grp")
//    public ResponseEntity<String> deleteGameRoomPhoto(
//            @PathVariable("username") String username) {
//        userService.deleteGameRoomPhoto(username);
//        return ResponseEntity.ok(username + "'s game room photo has been deleted ");
//    }

    // DeleteMapping to delete a user by username
    @DeleteMapping("/admin/users/{username}")
    public ResponseEntity<String> deleteUserByUsername(
            @PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok(username + " has been deleted ");
    }

    // PutMapping to change a user's password
    @PutMapping("/users/{username}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable("username") String username,
            @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changeUserPassword(username, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
        return ResponseEntity.ok(username + "'s password has been changed ");
    }

    // PutMapping to change a user's email
    @PutMapping("/users/{username}/change-email")
    public ResponseEntity<String> changeEmail(
            @PathVariable("username") String username,
            @Valid @RequestBody EmailChangeRequest emailChangeRequest) {
        userService.changeUserEmail(username, emailChangeRequest.getNewEmail());
        return ResponseEntity.ok(username + "'s email has been changed ");
    }

}
