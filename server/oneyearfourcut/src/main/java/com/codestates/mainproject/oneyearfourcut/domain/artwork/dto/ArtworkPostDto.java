package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ArtworkPostDto {

    private MultipartFile image;
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Size(min = 1, max = 20, message = "제목은 최소 1 이상 20 이하까지 작성할 수 있습니다.")
    private String title;
    @NotBlank(message = "설명은 필수 입력 값입니다.")
    @Size(min = 1, max = 70, message = "설명은 최소 1 이상 70 이하까지 작성할 수 있습니다.")
    private String content;

    @Builder
    public ArtworkPostDto(MultipartFile image, String title, String content) {
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
