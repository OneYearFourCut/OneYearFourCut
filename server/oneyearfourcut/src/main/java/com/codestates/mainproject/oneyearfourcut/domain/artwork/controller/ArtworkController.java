package com.codestates.mainproject.oneyearfourcut.domain.artwork.controller;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkListResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.mapper.ArtworkMapper;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galleries")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkMapper artworkMapper;
    private final ArtworkService artworkService;

    // 전시 작품 등록
    @PostMapping("/{gallery-id}/artworks")
    public ResponseEntity<?> postArtwork(@PathVariable("gallery-id") long galleryId,
                                      @ModelAttribute ArtworkRequestDto request) {
        Artwork artwork = artworkMapper.artworkRequestDtoToArtwork(request);
        artworkService.createArtwork(galleryId, artwork);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 작품 전체 조회
    @GetMapping("/{gallery-id}/artworks")
    public ResponseEntity<?> getArtworks(@PathVariable("gallery-id") long galleryId) {

        List<Artwork> artworkList = artworkService.findArtworkList(galleryId);
        List<ArtworkResponseDto> response = artworkMapper.artworkListToArtworkListResponseDto(artworkList);

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
