package com.example.rgjalpha1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public String username;
    public String email;
    public String profilePhotoFileName;
    public byte[] profilePhotoData;
    public String gameRoomPhotoFileName;
    public byte[] gameRoomPhotoData;
    public Boolean isEnabled;

}
