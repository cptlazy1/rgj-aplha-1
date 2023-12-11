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


    public Long gameConditionID;
    // Null checks are done in the service layer
    public Boolean hasManual;
    public Boolean hasCase;
    public Boolean hasScratches;
    public Boolean hasStickers;
    public Boolean hasWriting;
}
