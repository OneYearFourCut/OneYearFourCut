package com.codestates.mainproject.oneyearfourcut.domain.gallery.dto;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GalleryRequestDto {
    private String title;
    private String content;

    @Builder    //테스트코드용 생성자
    public GalleryRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Gallery toEntity(long memberId) {
        return Gallery.builder()
                .title(this.title)
                .content(this.content)
                .status(GalleryStatus.OPEN)
                .member(new Member(memberId))
                .build();
    }
}
