package com.example.rgjalpha1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Size(min = 1, max = 50, message = "Game name must be between 1 and 50 characters")
    public String gameName;
    @Min(value = 1000, message = "Year of release must be 4 digits")
    @Max(value = 9999, message = "Year of release must be 4 digits")
    public Integer gameYearOfRelease;
    public String gamePublisher;
    public Boolean gameIsOriginal;
    public String systemName;

}
