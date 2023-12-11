package com.example.rgjalpha1.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Game condition Has Manual cannot be null")
    public Boolean hasManual;

    @NotNull(message = "Game condition Has Case cannot be null")
    public Boolean hasCase;

    @NotNull(message = "Game condition Has Stickers cannot be null")
    public Boolean hasScratches;

    @NotNull(message = "Game condition Has Scratches cannot be null")
    public Boolean hasStickers;

    @NotNull(message = "Game condition Has Writing cannot be null")
    public Boolean hasWriting;
}
