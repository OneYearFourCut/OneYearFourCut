package com.codestates.mainproject.oneyearfourcut.domain.gallery.controller;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.mapper.GalleryMapper;
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
    private final GalleryMapper galleryMapper;

    //전시관 등록
    @PostMapping
    public ResponseEntity postGallery(@RequestBody GalleryRequestDto galleryRequestDto) {
        //@LoginMember -> 현재 로그인한 member id를 가져올 수 있다

        Gallery requestGallery = galleryMapper.galleryRequestDtoToGallery(galleryRequestDto);
        Gallery savedGallery = galleryService.createGallery(requestGallery, 1L);
        GalleryResponseDto galleryResponseDto = galleryMapper.galleryToGalleryResponseDto(savedGallery);

        return new ResponseEntity<>(galleryResponseDto, HttpStatus.CREATED);
    }

    //전시관 조회
    @GetMapping("/{gallery-id}")
    public ResponseEntity getGallery(@PathVariable("gallery-id") Long galleryId) {
        Gallery findGallery = galleryService.findGallery(galleryId);

        GalleryResponseDto galleryResponseDto = galleryMapper.galleryToGalleryResponseDto(findGallery);

        return new ResponseEntity<>(galleryResponseDto, HttpStatus.OK);
    }

    //전시관 수정
    @PatchMapping("/{gallery-id}")
    public ResponseEntity patchGallery(@RequestBody GalleryRequestDto galleryRequestDto,
                                       @PathVariable("gallery-id") Long galleryId) {

        Gallery findGallery = galleryService.findGallery(galleryId);
        Gallery requestGallery = galleryMapper.galleryRequestDtoToGallery(galleryRequestDto);

        galleryService.modifyGallery(requestGallery, findGallery);
        return new ResponseEntity(HttpStatus.OK);
    }

    //전시관 폐쇄
    @DeleteMapping("/{gallery-id}")
    public ResponseEntity deleteGallery(@PathVariable("gallery-id") Long galleryId) {
        galleryService.deleteGallery(galleryId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
