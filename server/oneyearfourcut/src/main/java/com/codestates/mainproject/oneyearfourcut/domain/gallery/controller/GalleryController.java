package com.codestates.mainproject.oneyearfourcut.domain.gallery.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/galleries")
@RequiredArgsConstructor
@Validated
public class GalleryController {
    private final GalleryService galleryService;

    //전시관 등록
    @PostMapping
    public ResponseEntity postGallery(@Valid @RequestBody GalleryRequestDto galleryRequestDto,
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
    @PatchMapping("/me")
    public ResponseEntity patchGallery(@Valid @RequestBody GalleryPatchDto galleryPatchDto,
                                       @LoginMember Long memberId) {
        galleryService.modifyGallery(galleryPatchDto, memberId);

        return new ResponseEntity("전시관 수정 성공", HttpStatus.OK);
    }

//    전시관 폐쇄
    @DeleteMapping("/me")
    public ResponseEntity deleteGallery(@LoginMember Long memberId) {
        galleryService.deleteGallery(memberId);

        return new ResponseEntity("전시관 삭제 성공", HttpStatus.NO_CONTENT);
    }
}
