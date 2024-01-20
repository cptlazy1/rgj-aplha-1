package com.example.rgjalpha1.dto;

import com.example.rgjalpha1.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailChangeRequest {

    @ValidEmail
    private String newEmail;

}
