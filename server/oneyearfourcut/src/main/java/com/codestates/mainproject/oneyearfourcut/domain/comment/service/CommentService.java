package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEventPublisher;
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
    private final AlarmEventPublisher alarmEventPublisher;

    @Transactional
    public CommentGalleryHeadDto<Object> createCommentOnGallery(CommentRequestDto commentRequestDto, Long galleryId, Long memberId) {
        Comment comment = commentRequestDto.toCommentEntity();
        Member member = memberService.findMember(memberId);
        Gallery gallery = galleryService.findGallery(galleryId);

        comment.setMember(member);
        comment.setGallery(gallery);

        commentRepository.save(comment);

        //알림 생성
        Long receiverId = gallery.getMember().getMemberId();
        alarmEventPublisher.publishAlarmEvent(AlarmEvent.builder()
                .receiverId(receiverId)
                .senderId(memberId)
                .alarmType(AlarmType.COMMENT_GALLERY)
                .galleryId(galleryId)
                .artworkId(null)
                .build());

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

        //전시관 주인에게 알림 생성
        Long galleryReceiverId = gallery.getMember().getMemberId();
        alarmEventPublisher.publishAlarmEvent(AlarmEvent.builder()
                .receiverId(galleryReceiverId)
                .senderId(memberId)
                .alarmType(AlarmType.COMMENT_ARTWORK)
                .galleryId(galleryId)
                .artworkId(artworkId)
                .build());

        //작품 주인에게 알림 생성
        Long artworkReceiverId = artwork.getMember().getMemberId();
        if (artworkReceiverId != galleryReceiverId) {   //자기 전시관에 단 작품이면 알람이 한 번만 오도록 처리
            alarmEventPublisher.publishAlarmEvent(AlarmEvent.builder()
                    .receiverId(artworkReceiverId)
                    .senderId(memberId)
                    .alarmType(AlarmType.COMMENT_ARTWORK)
                    .galleryId(galleryId)
                    .artworkId(artworkId)
                    .build());
        }

        return new CommentArtworkHeadDto<>(galleryId, artworkId, comment.toCommentArtworkResponseDto());
    }

    public CommentGalleryPageResponseDto<Object> getGalleryCommentPage(Long galleryId, int page, int size){
        PageRequest pr = PageRequest.of(page - 1, size);
        // 갤러리 안에 있는 등록중인 작품에 대한 댓글 조회
        Page<Comment> commentPage = commentRepository
                .findAllByCommentStatusAndGallery_GalleryIdAndArtwork_StatusOrderByCommentIdDesc(VALID, galleryId, ArtworkStatus.REGISTRATION, pr);

        /* 댓글 리스트가 없을 경우 빈 배열로 응답하는 게 아니었나요?? */
        if (commentPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "댓글이 아직 없습니다.");
        }
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());
        List<Comment> commentList = commentPage.getContent();

        List<CommentGalleryResDto> collect = CommentGalleryResDto.toCommentGalleryResponseDtoList(commentList);
        return new CommentGalleryPageResponseDto<>(galleryId, collect, pageInfo);
    }

    public CommentArtworkPageResponseDto<Object> getArtworkCommentPage(Long galleryId, Long artworkId, int page, int size) {
        artworkService.checkGalleryArtworkVerification(galleryId, artworkId);
        PageRequest pr = PageRequest.of(page - 1, size);
        // artworkId만 검증하면 굳이 '등록중'이라는 조건을 추가할 필요는 없음.
        Page<Comment> commentPage =
                commentRepository.findAllByCommentStatusAndArtwork_ArtworkIdOrderByCommentIdDesc(VALID, artworkId, pr);

        /* 댓글 리스트가 없을 경우 빈 배열로 응답하는 게 아니었나요?? */
        if (commentPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "댓글이 아직 없습니다.");
        }
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());
        List<Comment> commentList = commentPage.getContent();

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
        return new CommentGalleryHeadDto<>(galleryId, foundComment.toCommentGalleryResponseDto());
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
