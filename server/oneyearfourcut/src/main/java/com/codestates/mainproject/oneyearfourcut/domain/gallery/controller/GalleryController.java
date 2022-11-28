package com.codestates.mainproject.oneyearfourcut.domain.gallery.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/galleries")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryService galleryService;

    //전시관 등록
    @PostMapping
    public ResponseEntity postGallery(@RequestBody GalleryRequestDto galleryRequestDto,
                                      @LoginMember Long memberId) {
        GalleryResponseDto galleryResponseDto = galleryService.createGallery(galleryRequestDto, memberId);

        return new ResponseEntity<>(galleryResponseDto, HttpStatus.CREATED);
    }

    //전시관 조회
    @GetMapping("/{gallery-id}")
    public ResponseEntity getGallery(@PathVariable("gallery-id") Long galleryId) {
        Gallery findGallery = galleryService.findGallery(galleryId);

        return new ResponseEntity<>(findGallery.toGalleryResponseDto(), HttpStatus.OK);
    }

    //전시관 수정
    @PatchMapping("/{gallery-id}")
    public ResponseEntity patchGallery(@RequestBody GalleryRequestDto galleryRequestDto,
                                       @PathVariable("gallery-id") Long galleryId,
                                       @LoginMember Long memberId) {
        galleryService.modifyGallery(galleryRequestDto, galleryId, memberId);

        return new ResponseEntity("전시관 수정 성공", HttpStatus.OK);
    }

    //전시관 폐쇄
    @DeleteMapping("/{gallery-id}")
    public ResponseEntity deleteGallery(@PathVariable("gallery-id") Long galleryId,
                                        @LoginMember Long memberId) {
        galleryService.deleteGallery(galleryId, memberId);

        return new ResponseEntity("전시관 삭제 성공", HttpStatus.NO_CONTENT);
    }
}
