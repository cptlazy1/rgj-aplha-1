package com.example.rgjalpha1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameConditionDto {

    // TODO: Add validation for the game condition fields
    public Long gameConditionID;
    public Boolean hasManual;
    public Boolean hasCase;
    public Boolean hasScratches;
    public Boolean hasStickers;
    public Boolean hasWriting;
}
