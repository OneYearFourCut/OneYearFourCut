package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ArtworkResponseDto {

    private long artworkId;
    private long memberId;
    private String title;
    private String content;
    private String imagePath;
    private int likeCount;
    private boolean liked;
    private int commentCount;

    @Builder
    private ArtworkResponseDto(long artworkId, long memberId, String title, String content, String imagePath, int likeCount, boolean liked, int commentCount) {
        this.artworkId = artworkId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.likeCount = likeCount;
        this.liked = liked;
        this.commentCount = commentCount;
    }


    public static List<ArtworkResponseDto> toListResponse (List<Artwork> artworkList) {
        return artworkList.stream()
                .map(Artwork::toArtworkResponseDto)
                .collect(Collectors.toList());
    }
}
