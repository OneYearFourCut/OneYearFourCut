package com.codestates.mainproject.oneyearfourcut.domain.gallery.dto;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.validator.NotSpace;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class GalleryPatchDto {
    @NotSpace
    @Size(min = 1, max = 15, message = "제목은 최소 1 이상 15 이하까지 작성할 수 있습니다.")
    private String title;
    @NotSpace
    @Size(min = 1, max = 30, message = "설명은 최소 1 이상 30 이하까지 작성할 수 있습니다.")
    private String content;

    @Builder    //테스트코드용 생성자
    public GalleryPatchDto(String title, String content) {
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
