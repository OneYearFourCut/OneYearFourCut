package com.codestates.mainproject.oneyearfourcut.domain.artwork.service;

import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.LikeStatus;
import com.codestates.mainproject.oneyearfourcut.domain.Like.repository.ArtworkLikeRepository;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.controller.CommentController;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.aws.service.AwsS3Service;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final GalleryService galleryService;

    private final MemberService memberService;
    private final ArtworkLikeRepository artworkLikeRepository;
    private final AwsS3Service awsS3Service;
    private final AlarmService alarmService;
    private final CommentRepository commentRepository;


    @Transactional
    public ArtworkResponseDto createArtwork(long memberId, long galleryId, ArtworkPostDto requestDto) {
        galleryService.verifiedGalleryExist(galleryId);
        Artwork artwork = requestDto.toEntity();
        // 이미지 유효성(null) 검증
        if (artwork.getImage() == null) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND_FROM_REQUEST);
        }
        artwork.setGallery(new Gallery(galleryId));
        artwork.setMember(new Member(memberId));

        String imageRoot = awsS3Service.uploadFile(artwork.getImage());
        artwork.setImagePath(imageRoot);
        Artwork savedArtwork = artworkRepository.save(artwork);

        //알람 생성
        Long artworkId = savedArtwork.getArtworkId();
        alarmService.createAlarmBasedOnArtwork(artworkId, galleryId, memberId, AlarmType.POST_ARTWORK);

        return savedArtwork.toArtworkResponseDto();
    }

    public ArtworkResponseDto findArtwork(long memberId, long galleryId, long artworkId) {
        galleryService.verifiedGalleryExist(galleryId);

        Artwork verifiedArtwork = findVerifiedArtwork(galleryId, artworkId);

        if (memberId != -1) {
            boolean isLiked =
                    artworkLikeRepository.existsByMember_MemberIdAndArtwork_ArtworkIdAndStatus(memberId, artworkId, LikeStatus.LIKE);
            verifiedArtwork.setLiked(isLiked);
        }
        return verifiedArtwork.toArtworkResponseDto();
    }

    public List<ArtworkResponseDto> findArtworkList(long memberId, long galleryId) {
        galleryService.verifiedGalleryExist(galleryId);

        List<Artwork> artworkList = artworkRepository.findAllByGallery_GalleryIdAndStatus(galleryId,
                ArtworkStatus.REGISTRATION, Sort.by(desc("createdAt")));

        if (memberId != -1) {
            Member loginMember = memberService.findMember(memberId);
            List<ArtworkLike> memberLikeList = loginMember.getArtworkLikeList();
            memberLikeList.
                    stream().filter(like -> like.getStatus().equals(LikeStatus.LIKE)).
                    forEach(like -> like.getArtwork()
                            .setLiked(artworkList.contains(like.getArtwork())));
        }
        return ArtworkResponseDto.toListResponse(artworkList);
    }

    public List<OneYearFourCutResponseDto> findOneYearFourCut(long galleryId) {
        galleryService.verifiedGalleryExist(galleryId);

        List<Artwork> findArtworkList = artworkRepository.findTop4ByGallery_GalleryIdAndStatus(galleryId,
                ArtworkStatus.REGISTRATION, Sort.by(desc("likeCount"), desc("createdAt")));

        return OneYearFourCutResponseDto.toListResponse(findArtworkList);
    }

    @Transactional
    public ArtworkResponseDto updateArtwork(long memberId, long galleryId, long artworkId, ArtworkPatchDto request) {
        galleryService.verifiedGalleryExist(galleryId);

        Artwork findArtwork = findVerifiedArtwork(galleryId, artworkId);
        verifyAuthority(memberId, findArtwork);

        Artwork artwork = request.toEntity();

        Optional<MultipartFile> image = Optional.ofNullable(artwork.getImage());

        if (image.isPresent()) {
            String s3Path = awsS3Service.uploadFile(image.get());
            awsS3Service.deleteImage(findArtwork.getImagePath());
            artwork.setImagePath(s3Path);
        }

        findArtwork.modify(artwork);

        return findArtwork.toArtworkResponseDto();
    }
    @Transactional
    public void deleteArtwork(long memberId, long galleryId, long artworkId) {
        galleryService.verifiedGalleryExist(galleryId);
        Artwork findArtwork = findVerifiedArtwork(galleryId, artworkId);
        verifyAuthority(memberId, findArtwork);
        // 댓글 삭제 (상태 변경)
        List<Comment> comments = commentRepository.findAllByArtworkId(artworkId);
        comments.forEach(comment -> comment.changeCommentStatus(CommentStatus.DELETED));

        findArtwork.setStatus(ArtworkStatus.DELETED);
    }

    // ================= 검증 관련 메서드 =================
    public Artwork findVerifiedArtwork(long galleryId, long artworkId) {
        Optional<Artwork> artworkOptional = artworkRepository.findById(artworkId);

        Artwork findArtwork = artworkOptional.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND));

        if (galleryId != findArtwork.getGallery().getGalleryId()) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND_FROM_GALLERY);
        }
        // 작품이 삭제된 상태가 아닌가? 검증
        if (findArtwork.getStatus().equals(ArtworkStatus.DELETED)) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_DELETED);
        }

        return findArtwork;
    }

    public void checkGalleryArtworkVerification(Long galleryId, Long artworkId) {
        Optional<Artwork> artwork = artworkRepository.findById(artworkId);
        Artwork foundArtwork = artwork.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND));

        if ((!Objects.equals(galleryId, foundArtwork.getGallery().getGalleryId()))) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND_FROM_GALLERY);
        }
        // 작품이 삭제된 상태가 아닌가? 검증
        if (foundArtwork.getStatus().equals(ArtworkStatus.DELETED)) {
            throw new BusinessLogicException(ExceptionCode.ARTWORK_DELETED);
        }
    }

    private void verifyAuthority(long memberId, Artwork artwork) {
        boolean isWriter = artwork.getMemberId() == memberId;
        boolean isAdmin = artwork.getGallery().getMember().getMemberId() == memberId;
        if (!(isWriter || isAdmin)) { // 둘 다 false일 경우 권한 없음
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

}

















