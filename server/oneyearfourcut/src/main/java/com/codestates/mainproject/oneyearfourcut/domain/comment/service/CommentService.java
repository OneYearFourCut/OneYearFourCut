package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import com.codestates.mainproject.oneyearfourcut.global.page.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.DELETED;
import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final GalleryService galleryService;
    private final ArtworkService artworkService;
    private final AlarmService alarmService;

    @Transactional
    public CommentGalleryHeadDto<Object> createCommentOnGallery(CommentRequestDto commentRequestDto, Long galleryId, Long memberId) {
        Comment comment = commentRequestDto.toCommentEntity();
        Member member = memberService.findMember(memberId);
        Gallery gallery = galleryService.findGallery(galleryId);

        comment.setMember(member);
        comment.setGallery(gallery);

        commentRepository.save(comment);
        alarmService.createAlarmBasedOnGallery(galleryId, memberId, AlarmType.COMMENT_GALLERY);
        return new CommentGalleryHeadDto<>(galleryId, comment.toCommentGalleryResponseDto());
    }

    @Transactional
    public CommentArtworkHeadDto<Object> createCommentOnArtwork(CommentRequestDto commentRequestDto, Long galleryId, Long artworkId, Long memberId) {
        Member member = memberService.findMember(memberId);
        Gallery gallery = galleryService.findGallery(galleryId);
        Artwork artwork = artworkService.findVerifiedArtwork(galleryId, artworkId);

        Comment comment = commentRequestDto.toCommentEntity();
        comment.setMember(member);
        comment.setGallery(gallery);
        comment.setArtwork(artwork);

        commentRepository.save(comment);

        alarmService.createAlarmBasedOnArtworkAndGallery(artworkId, galleryId, memberId, AlarmType.COMMENT_ARTWORK);
        return new CommentArtworkHeadDto<>(galleryId, artworkId, comment.toCommentArtworkResponseDto());
    }


    @Transactional(readOnly = true)
    public Page<Comment> findCommentByPage(Long galleryId, Long artworkId, int page, int size) {
        PageRequest pr = PageRequest.of(page - 1, size);
        Page<Comment> commentPage;
        galleryService.findGallery(galleryId);
        if (artworkId == null) {
            commentPage =
                    commentRepository.findAllByCommentStatusAndGallery_GalleryIdOrderByCommentIdDesc(VALID,galleryId, pr);
        }
        else {
            artworkService.checkGalleryArtworkVerification(galleryId, artworkId);
            commentPage = commentRepository.findAllByCommentStatusAndArtworkIdOrderByCommentIdDesc(VALID, artworkId, pr);
        }
        if (commentPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "댓글이 아직 없습니다.");
        }
        return commentPage;
    }


    @Transactional(readOnly = true)
    public CommentGalleryPageResponseDto<Object> getGalleryCommentPage(Long galleryId, int page, int size){
        /*memberService.findMember(memberId);*/
        Page<Comment> commentPage = findCommentByPage(galleryId, null, page, size);
        List<Comment> commentList = commentPage.getContent();
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());

        List<CommentGalleryResDto> collect = commentList.stream()
                .map(comment -> {
                    Long artworkId = comment.getArtworkId();    // 해당하는 작품 id 를 찾고
                    String imagePath = null;
                    if (artworkId != null) {
                        Artwork verifiedArtwork = artworkService.findVerifiedArtwork(comment.getGallery().getGalleryId(), artworkId);
                        imagePath = verifiedArtwork.getImagePath(); //id 로 artwork 엔티티에 접근해서 imagePath 받아오고
                    }
                    return comment.toCommentGalleryResponseDto(imagePath); //
                })
                .collect(Collectors.toList());

        return new CommentGalleryPageResponseDto<>(galleryId, collect, pageInfo);
    }

    @Transactional(readOnly = true)
    public CommentArtworkPageResponseDto<Object> getArtworkCommentPage(Long galleryId, Long artworkId, int page, int size) {
        /*memberService.findMember(memberId);*/
        Page<Comment> commentPage = findCommentByPage(galleryId, artworkId, page, size);
        List<Comment> commentList = commentPage.getContent();
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());
        List<CommentArtworkResDto> response = CommentArtworkResDto.toCommentArtworkResponseDtoList(commentList);
        return new CommentArtworkPageResponseDto<>(galleryId, artworkId, response, pageInfo);
    }

    @Transactional(readOnly = true)
    public Comment findComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        Comment foundComment = comment.orElseThrow(()->new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(foundComment.getCommentStatus() == DELETED) throw new BusinessLogicException(ExceptionCode.COMMENT_DELETED);
        return foundComment;
    }

    @Transactional
    public void deleteComment(Long galleryId, Long commentId, Long memberId) {
        Comment foundComment= findComment(commentId);
        checkGalleryCommentVerification(galleryId, commentId, memberId);
        checkCommentMemberVerification(commentId,memberId);
        //--검증완료--
        foundComment.changeCommentStatus(DELETED);
    }

    @Transactional
    public CommentGalleryHeadDto<Object> modifyComment(Long galleryId, Long commentId, CommentRequestDto commentRequestDto, Long memberId){
        Comment foundComment = findComment(commentId);
        checkGalleryCommentVerification(galleryId, commentId, memberId);
        checkCommentMemberVerification(commentId,memberId);
        //--검증완료--
        Comment requestComment = commentRequestDto.toCommentEntity();
        Optional.ofNullable(requestComment.getContent())
                .ifPresent(foundComment::changeContent);
        return new CommentGalleryHeadDto<>(galleryId, foundComment.toCommentGalleryResponseDto(null));
    }

    @Transactional(readOnly = true)
    public void checkGalleryCommentVerification(Long galleryId, Long commentId, Long memberId) {
        Comment foundComment = findComment(commentId);
        memberService.findMember(memberId);
        galleryService.findGallery(galleryId);
        if (!Objects.equals(galleryId, foundComment.getGallery().getGalleryId())) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND_FROM_GALLERY);
        }
    }

    @Transactional(readOnly = true)
    public void checkCommentMemberVerification(Long commentId, Long memberId) {
        Comment comment = findComment(commentId);
        Long foundCommentMemberId = comment.getMember().getMemberId();
        if (memberId != foundCommentMemberId && comment.getGallery().getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }
}
