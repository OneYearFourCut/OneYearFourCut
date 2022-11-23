package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
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

    public static ArtworkResponseDto of(Artwork artwork, boolean voted) {
        return ArtworkResponseDto.builder()
                .artworkId(artwork.getArtworkId())
                .memberId(artwork.getMemberId())
                .title(artwork.getTitle()).content(artwork.getContent())
                .imagePath(artwork.getImagePath())
                .likeCount(artwork.getLikeCount())
                .liked(voted)
                .commentCount(artwork.getCommentCount()) // 수정 예정
                .build();
    }

    public static ArtworkResponseDto of(Artwork artwork) {
        return ArtworkResponseDto.builder()
                .artworkId(artwork.getArtworkId())
                .imagePath(artwork.getImagePath())
                .likeCount(artwork.getLikeCount())
                .build();
    }
}
