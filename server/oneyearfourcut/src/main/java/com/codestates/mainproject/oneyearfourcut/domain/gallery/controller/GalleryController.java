package com.codestates.mainproject.oneyearfourcut.domain.gallery.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/galleries")
public class GalleryController {
    //전시관 등록
    @PostMapping
    public ResponseEntity postGallery(@RequestBody GalleryRequestDto galleryRequestDto) {
        // jwt 토큰으로 회원id를 조회
        // 활성화된 전시관이 이미 존재하는지 확인하고 있으면 에러, 없으면 전시관 생성
        GalleryResponseDto galleryResponseDto = GalleryResponseDto.builder()
                .createdAt(LocalDateTime.now())
                .galleryId(1L)
                .title(galleryRequestDto.getTitle())
                .content(galleryRequestDto.getContent())
                .build();

        return new ResponseEntity<>(galleryResponseDto, HttpStatus.CREATED);
    }

    //전시관 조회
    @GetMapping("/{gallery-id}")
    public ResponseEntity getGallery(@PathVariable("gallery-id") Long galleryId) {
        GalleryResponseDto galleryResponseDto = GalleryResponseDto.builder()
                .createdAt(LocalDateTime.now())
                .galleryId(1L)
                .title("승필의 전시회")
                .content("어서오세요")
                .build();

        return new ResponseEntity<>(galleryResponseDto, HttpStatus.OK);
    }

    //전시관 수정
    @PatchMapping("/{gallery-id}")
    public ResponseEntity patchGallery(@RequestBody GalleryRequestDto galleryRequestDto,
                                       @PathVariable("gallery-id") Long galleryId) {
        // 전시관 편집하는 로직
        return new ResponseEntity(HttpStatus.OK);
    }

    //전시관 폐쇄
    @DeleteMapping("/{gallery-id}")
    public ResponseEntity deleteGallery(@PathVariable("gallery-id") Long galleryId) {
        // 전시관 폐쇄하는 로직
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
