package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ArtworkRequestDto {

    private MultipartFile img;
    private String title;
    private String content;


}
