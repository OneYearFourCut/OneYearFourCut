package com.codestates.mainproject.oneyearfourcut.domain.artwork.controller;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkListResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galleries")
public class ArtworkController {

    // 전시 작품 등록
    @PostMapping("/{gallery-id}/artworks")
    public ResponseEntity<?> postArtwork(@PathVariable("gallery-id") long galleryId,
                                      @ModelAttribute ArtworkRequestDto request) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 작품 전체 조회
    @GetMapping("/{gallery-id}/artworks")
    public ResponseEntity<?> getArtworks(@PathVariable("gallery-id") long galleryId) {

        List<ArtworkResponseDto> artworks = List.of(
                new ArtworkResponseDto(1L, 1L, "타이틀1", "설명1", "이미지경로1", 5, true, 3),
                new ArtworkResponseDto(2L, 1L, "타이틀2", "설명2", "이미지경로2", 3, false, 3)
                );

        ArtworkListResponseDto response = new ArtworkListResponseDto("원강님의 전시관", "나의 전시관을 소개합니다!", artworks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 작품 개별 조회
    @GetMapping("{gallery-id}/artworks/{artwork-id}")
    public ResponseEntity<?> getArtwork(@PathVariable("gallery-id") long galleryId,
                                     @PathVariable("artwork-id") long artworkId) {
        ArtworkResponseDto response = new ArtworkResponseDto(artworkId, 1L,"타이틀1", "설명1", "이미지경로1", 5, false, 3);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 작품 수정
    @PatchMapping("/{gallery-id}/artworks/{artwork-id}")
    public ResponseEntity<?> patchArtwork(@PathVariable("gallery-id") long galleryId,
                                          @PathVariable("artwork-id") long artworkId,
                                          @ModelAttribute ArtworkRequestDto request) {
        ArtworkResponseDto response = new ArtworkResponseDto(artworkId, 1L,request.getTitle(), request.getContent(),
                "이미지경로/" + request.getImg().getOriginalFilename(),
                5, false, 3);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 작품 삭제
    @DeleteMapping("{gallery-id}/artworks/{artwork-id}")
    public ResponseEntity<?> deleteArtwork(@PathVariable("gallery-id") long galleryId,
                                        @PathVariable("artwork-id") long artworkId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
