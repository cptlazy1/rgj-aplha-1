package com.example.rgjalpha1.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 50)
    public String gameName;
    @Digits(integer = 4, fraction = 0)
    public Integer gameYearOfRelease; // Todo: validation must be between 1970 and current year and 4 digits
    public String gamePublisher;
    public Boolean gameIsOriginal;
    public String systemName;

}
