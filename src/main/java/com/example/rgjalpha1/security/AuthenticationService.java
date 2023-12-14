package com.example.rgjalpha1.security;

import com.example.rgjalpha1.dto.AuthenticationRequest;
import com.example.rgjalpha1.dto.AuthenticationResponse;
import com.example.rgjalpha1.dto.RegisterRequest;
import com.example.rgjalpha1.model.User;
import com.example.rgjalpha1.repository.UserRepository;
import com.example.rgjalpha1.role.Role;
import com.example.rgjalpha1.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    // Original register method with JWT
//    public AuthenticationResponse register(RegisterRequest registerRequest) {
//        var user = User
//                .builder()
//                .username(registerRequest.getUsername())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .email(registerRequest.getEmail())
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//
//        var jwToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .jwToken(jwToken)
//                .build();w2
//    }

    // Alternative register method without JWT
    // ToDo: add a check if the username already exists. Like this example:
//    public void addStudent(Student student) {
//        Boolean existsEmail = studentRepository
//                .selectExistsEmail(student.getEmail());
//        if (existsEmail) {
//            throw new BadRequestException(
//                    "Email " + student.getEmail() + " taken");
//        }
//
//        studentRepository.save(student);
//    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User
                .builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthenticationResponse.builder().build();
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                ));
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwToken(jwToken)
                .build();
    }
}
