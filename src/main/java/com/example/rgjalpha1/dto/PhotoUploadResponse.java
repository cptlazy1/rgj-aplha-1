package com.example.rgjalpha1.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoUploadResponse {

    public String fileName;
    public String contentType;
    public String fileDownloadUrl;
}
