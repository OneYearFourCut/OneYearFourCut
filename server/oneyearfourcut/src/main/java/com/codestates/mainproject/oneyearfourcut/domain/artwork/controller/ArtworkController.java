package com.codestates.mainproject.oneyearfourcut.domain.artwork.controller;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/galleries")
@RequiredArgsConstructor
@Validated
public class ArtworkController {

    private final ArtworkService artworkService;

    // 전시 작품 등록
    @PostMapping("/{gallery-id}/artworks")
    public ResponseEntity<?> postArtwork(@LoginMember Long memberId,
                                         @Positive @PathVariable("gallery-id") long galleryId,
                                         @Valid @ModelAttribute ArtworkPostDto request) {
        artworkService.createArtwork(memberId, galleryId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 작품 전체 조회
    @GetMapping("/{gallery-id}/artworks")
    public ResponseEntity<?> getArtworks(@LoginMember Long memberId,
                                         @Positive @PathVariable("gallery-id") long galleryId) {

        List<ArtworkResponseDto> response = artworkService.findArtworkList(memberId, galleryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 작품 개별 조회
    @GetMapping("{gallery-id}/artworks/{artwork-id}")
    public ResponseEntity<?> getArtwork(@LoginMember Long memberId,
                                        @Positive @PathVariable("gallery-id") long galleryId,
                                        @Positive @PathVariable("artwork-id") long artworkId) {

        ArtworkResponseDto response = artworkService.findArtwork(memberId, galleryId, artworkId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 작품 수정
    @PatchMapping("/{gallery-id}/artworks/{artwork-id}")
    public ResponseEntity<?> patchArtwork(@LoginMember Long memberId,
                                          @Positive @PathVariable("gallery-id") long galleryId,
                                          @Positive @PathVariable("artwork-id") long artworkId,
                                          @Valid @ModelAttribute ArtworkPatchDto request) {

        ArtworkResponseDto response = artworkService.updateArtwork(memberId, galleryId, artworkId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 올해네컷 조회
    @GetMapping("/{gallery-id}/artworks/like")
    public ResponseEntity<?> getOneYearFourCut(@Positive @PathVariable("gallery-id") long galleryId) {

        List<OneYearFourCutResponseDto> response = artworkService.findOneYearFourCut(galleryId);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    // 작품 삭제
    @DeleteMapping("{gallery-id}/artworks/{artwork-id}")
    public ResponseEntity<?> deleteArtwork(@LoginMember Long memberId,
                                           @Positive @PathVariable("gallery-id") long galleryId,
                                           @Positive @PathVariable("artwork-id") long artworkId) {

        artworkService.deleteArtwork(memberId, galleryId, artworkId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
