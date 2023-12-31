package com.example.rgjalpha1.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {


    public Long gameID;

    @Size(min = 1, max = 50, message = "Game name must be between 1 and 50 characters")
    public String gameName;

    @Min(value = 1000, message = "Year of release must be 4 digits")
    @Max(value = 9999, message = "Year of release must be 4 digits")
    public Integer gameYearOfRelease;

    @Size(min = 1, max = 50, message = "Publisher name must be between 1 and 50 characters")
    public String gamePublisher;

    @NotNull(message = "Game is original must be true or false")
    public Boolean gameIsOriginal;

    @Size(min = 1, max = 50, message = "System name must be between 1 and 50 characters")
    public String systemName;

}
