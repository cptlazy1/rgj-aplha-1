package com.example.rgjalpha1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSystemDto {

    // Todo: add validation for each field
    public Long gameSystemID;
    public String gameSystemName;
    public String gameSystemBrand;
    public int gameSystemYearOfRelease;
    public Boolean isReadyToPlay;


}
