package com.example.rgjalpha1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSystemDto {

    public Long gameSystemID;

    @Size(min = 1, max = 50, message = "Game system name must be between 1 and 50 characters")
    public String gameSystemName;

    @Size(min = 1, max = 50, message = "Game system brand must be between 1 and 50 characters")
    public String gameSystemBrand;

    @Min(value = 1000, message = "Year of release must be 4 digits")
    @Max(value = 9999, message = "Year of release must be 4 digits")
    public Integer gameSystemYearOfRelease;

    @NotNull(message = "Game system Is Ready To Play must be specified")
    public Boolean isReadyToPlay;


}
