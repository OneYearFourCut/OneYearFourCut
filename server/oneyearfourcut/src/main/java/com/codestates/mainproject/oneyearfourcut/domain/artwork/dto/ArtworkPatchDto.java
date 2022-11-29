package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.global.validator.NotSpace;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Setter
@Getter
public class ArtworkPatchDto {

    private MultipartFile image;
    @NotSpace
    @Size(min = 1, max = 15, message = "제목은 최소 1 이상 15 이하까지 작성할 수 있습니다.")
    private String title;
    @NotSpace
    @Size(min = 1, max = 30, message = "설명은 최소 1 이상 30 이하까지 작성할 수 있습니다.")
    private String content;

    @Builder
    public ArtworkPatchDto(MultipartFile image, String title, String content) {
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
