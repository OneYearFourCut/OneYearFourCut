package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ArtworkRequestDto {

    private MultipartFile image;
    private String title;
    private String content;

    @Builder
    public ArtworkRequestDto(MultipartFile image, String title, String content) {
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public Artwork toEntity() {
        return Artwork.builder()
                .image(image)
                .title(title)
                .content(content)
                .build();
    }
}
