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
    public Boolean isCompleteInBox;
    public Boolean hasManual;
    public Boolean hasCase;
}
