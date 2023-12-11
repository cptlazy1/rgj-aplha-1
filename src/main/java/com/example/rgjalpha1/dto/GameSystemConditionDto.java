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
public class GameSystemConditionDto {

    public Long gameSystemConditionID;

    @NotNull(message = "Game system condition must be specified")
    public Boolean hasBox;

    @NotNull(message = "Game system condition must be specified")
    public Boolean hasCables;

    @NotNull(message = "Game system condition must be specified")
    public Boolean isModified;

}
