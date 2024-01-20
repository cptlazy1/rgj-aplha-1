package com.example.rgjalpha1.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {

    private String oldPassword;
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String newPassword;

}
