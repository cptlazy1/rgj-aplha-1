package com.example.rgjalpha1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {

    // Todo: add validation for all fields
    public Long gameID;
    public String gameName;
    public Integer gameYearOfRelease; // Todo: validation must be between 1970 and current year and 4 digits
    public String gamePublisher;
    public Boolean gameIsOriginal;
    public String systemName;

}
