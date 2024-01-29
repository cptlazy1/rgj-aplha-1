package com.example.rgjalpha1.dto;


import com.example.rgjalpha1.validation.UniqueUsername;
import com.example.rgjalpha1.validation.ValidEmail;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {


    @Size(min = 8, max = 20 , message = "Username must be between 8 and 20 characters")
    @UniqueUsername
    @Pattern(regexp = "^[a-z0-9_-]*$", message = "Username must contain only lowercase letters, numbers, underscore and hyphen")
    private String username;

    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String password;

//    @Email(message = "Please enter a valid email address")
    @ValidEmail
    private String email;

    private Boolean isEnabled = true;

}
