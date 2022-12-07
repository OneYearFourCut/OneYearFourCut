package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OneYearFourCutResponseDto {
    private long artworkId;
    private String imagePath;
    private int likeCount;

    @Builder
    private OneYearFourCutResponseDto(long artworkId, String imagePath, int likeCount) {
        this.artworkId = artworkId;
        this.imagePath = imagePath;
        this.likeCount = likeCount;
    }


    public static List<OneYearFourCutResponseDto> toListResponse (List<Artwork> artworkList) {
        return artworkList.stream()
                .map(Artwork::toOneYearFourCutResponseDto)
                .collect(Collectors.toList());
    }

}
