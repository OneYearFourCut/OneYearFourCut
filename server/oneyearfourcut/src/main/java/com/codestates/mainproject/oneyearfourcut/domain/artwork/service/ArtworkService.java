package com.codestates.mainproject.oneyearfourcut.domain.artwork.service;


import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.repository.ArtworkLikeRepository;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final GalleryService galleryService;

    private final MemberService memberService;
    private final ArtworkLikeRepository artworkLikeRepository;

    public void createArtwork(long memberId, long galleryId, ArtworkRequestDto requestDto) {
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

        Artwork artwork = requestDto.toEntity();
        artwork.setGallery(galleryService.findGallery(galleryId));
        artwork.setMember(new Member(memberId));

        // 이미지 - 로컬환경 : "/파일명.확장자"형태로 DB에 저장 (S3 설정 시 삭제 예정)
        String localImgRoot = "/" + artwork.getImage().getOriginalFilename();
        artwork.setImagePath(localImgRoot);

        artwork.setStatus(ArtworkStatus.REGISTRATION);
        artworkRepository.save(artwork);
    }

    @Transactional(readOnly = true)
    public ArtworkResponseDto findArtwork(long memberId, long galleryId, long artworkId) {
        galleryService.verifiedGalleryExist(galleryId);

        Artwork verifiedArtwork = findVerifiedArtwork(galleryId, artworkId);
        boolean isLiked =
                artworkLikeRepository.existsByMember_MemberIdAndArtwork_ArtworkId(memberId, artworkId);
        verifiedArtwork.setLiked(isLiked);

        return verifiedArtwork.toArtworkResponseDto();
    }

    @Transactional(readOnly = true)
    public List<ArtworkResponseDto> findArtworkList(long memberId, long galleryId) {
        galleryService.verifiedGalleryExist(galleryId);
        Member loginMember = memberService.findMember(memberId);

        List<Artwork> artworkList = artworkRepository.findAllByGallery_GalleryIdAndStatus(galleryId,
                Sort.by(desc("createdAt")), ArtworkStatus.REGISTRATION);

        // 가져온 작품 리스트가 비어 있을 경우 Exception 발생인데, 그냥 빈 배열로 줘도 괜찮을 듯 합니다.
        if (artworkList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND);
        }

        List<ArtworkLike> memberLikeList = loginMember.getArtworkLikeList();
        memberLikeList.
                forEach(like -> like.getArtwork()
                        .setLiked(artworkList.contains(like.getArtwork())));

        return ArtworkResponseDto.toListResponse(artworkList);
    }

    @Transactional(readOnly = true)
    public List<OneYearFourCutResponseDto> findOneYearFourCut(long memberId, long galleryId) {
        galleryService.verifiedGalleryExist(galleryId);
        Member loginMember = memberService.findMember(memberId);

        List<Artwork> findArtworkList = artworkRepository.findTop4ByGallery_GalleryIdAndStatus(galleryId,
                Sort.by(desc("likeCount"), desc("createdAt")), ArtworkStatus.REGISTRATION);

        // 가져온 작품 리스트가 비어 있을 경우 Exception 발생인데, 그냥 빈 배열로 줘도 괜찮을 듯 합니다.
        if (findArtworkList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND);
        }
        List<ArtworkLike> memberLikeList = loginMember.getArtworkLikeList();

        memberLikeList.
                forEach(like -> like.getArtwork()
                        .setLiked(findArtworkList.contains(like.getArtwork())));

        return OneYearFourCutResponseDto.toListResponse(findArtworkList);
    }

    public ArtworkResponseDto updateArtwork(long memberId, long galleryId, long artworkId, ArtworkRequestDto request) {
        galleryService.verifiedGalleryExist(galleryId);

        Artwork artwork = request.toEntity();
        Artwork findArtwork = findVerifiedArtwork(galleryId, artworkId);

        verifyAuthority(memberId, findArtwork);
        findArtwork.modify(artwork);

        return findArtwork.toArtworkResponseDto();
    }

    public void deleteArtwork(long memberId, long galleryId, long artworkId) {
        galleryService.verifiedGalleryExist(galleryId);
        Artwork findArtwork = findVerifiedArtwork(galleryId, artworkId);
        verifyAuthority(memberId, findArtwork);
        findArtwork.setStatus(ArtworkStatus.DELETED);
    }

    // ================= 검증 관련 메서드 =================
    @Transactional(readOnly = true)
    public Artwork findVerifiedArtwork(long galleryId, long artworkId) {
        Optional<Artwork> artworkOptional = artworkRepository.findById(artworkId);

        Artwork findArtwork = artworkOptional.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND));
        if (galleryId != findArtwork.getGallery().getGalleryId()) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND_FROM_GALLERY);
        }
        if (findArtwork.getGallery().getStatus() == GalleryStatus.CLOSED) {
            throw new BusinessLogicException(ExceptionCode.CLOSED_GALLERY);
        }

        return findArtwork;
    }

    private void verifyAuthority(long memberId, Artwork artwork) {
        boolean isWriter = artwork.getMemberId() == memberId;
        boolean isAdmin = artwork.getGallery().getMember().getMemberId() == memberId;
        if (!(isWriter || isAdmin)) { // 둘 다 false일 경우 권한 없음
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public void checkGalleryArtworkVerification(Long galleryId, Long artworkId) {
        Optional<Artwork> artwork = artworkRepository.findById(artworkId);
        Artwork foundArtwork = artwork.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND));

        if (!Objects.equals(galleryId, foundArtwork.getGallery().getGalleryId())) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND_FROM_GALLERY);
        }
    }
}

















