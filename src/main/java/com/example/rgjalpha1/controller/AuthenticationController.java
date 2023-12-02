package com.example.rgjalpha1.controller;

import com.example.rgjalpha1.dto.AuthenticationRequest;
import com.example.rgjalpha1.dto.AuthenticationResponse;
import com.example.rgjalpha1.dto.RegisterRequest;
import com.example.rgjalpha1.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        authenticationService.register(registerRequest);
        return ResponseEntity.ok("Account for user: " + registerRequest.getUsername() + " is created!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }
}
