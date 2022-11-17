package com.codestates.mainproject.oneyearfourcut.domain.artwork.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final GalleryRepository galleryRepository; // GalleryRepository -> GalleryService 로 변경 예정
    private final MemberRepository memberRepository; // MemberRepository -> MemberService 로 변경 예정

    public void createArtwork(long galleryId, Artwork artwork) {
        // galleryService - 검증된 갤러리 가져오는 메서드 구현시 아래 로직 대체할 예정
        Optional<Gallery> galleryOptional = galleryRepository.findById(galleryId);
        Gallery verifiedGallery = galleryOptional.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));
        artwork.setGallery(verifiedGallery);

        /*
        ### 이미지 업로드 관련
        MultipartFile img = artwork.getImg();
        // 파일명이 같더라도 충돌하지 않도록 UUID를 사용해 고유값을 넣도록 했습니다.
        String fileName = UUID.randomUUID() + "-" + img.getOriginalFilename();
        artwork.setImgPath(s3Url + fileName);

        ### 멤버 관련 - 토큰
        Member verifiedMember = memberRepository.findByEmail(SecurityContextHolder에서 가져온 유저 정보)
        artwork.setMember(verifiedMember);
        */

        // AccessToken 구현시 아래 로직 수정 예정
        Optional<Member> memberOptional = memberRepository.findById(2L);
        Member verifiedMember = memberOptional.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        artwork.setMember(verifiedMember);

        // 이미지 - 로컬환경 : "/파일명.확장자"형태로 DB에 저장 (S3 설정 시 삭제 예정)
        String localImgRoot = "/" + artwork.getImg().getOriginalFilename();
        artwork.setImgPath(localImgRoot);

        artworkRepository.save(artwork);
    }
    
    public Gallery findArtwork(Long artworkId) {
        return null;
    }
}
