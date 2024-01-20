package com.example.rgjalpha1.dto;

import com.example.rgjalpha1.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String jwToken;
    private String message;
    private Role role;
}
