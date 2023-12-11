package com.example.rgjalpha1.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSystemAndConditionDto {

    @Valid
    public GameSystemDto gameSystemDto;
    @Valid
    public GameSystemConditionDto gameSystemConditionDto;

}
