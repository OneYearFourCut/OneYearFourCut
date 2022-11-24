package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import com.codestates.mainproject.oneyearfourcut.global.page.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.DELETED;
import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final GalleryService galleryService;
    private final ArtworkService artworkService;

    public CommentGalleryHeadDto<Object> createCommentOnGallery(CommentRequestDto commentRequestDto, Long galleryId, Long memberId) {
        Comment comment = Comment.builder()
                .gallery(galleryService.findGallery(galleryId))
                .member(memberService.findMember(memberId))
                .content(commentRequestDto.getContent())
                .commentStatus(VALID)
                .build();
        commentRepository.save(comment);
        return new CommentGalleryHeadDto<>(galleryId,comment.toCommentGalleryResponseDto());
    }

    public CommentArtworkHeadDto<Object> createCommentOnArtwork(CommentRequestDto commentRequestDto, Long galleryId, Long artworkId, Long memberId) {
        artworkService.checkGalleryArtworkVerification(galleryId, artworkId);
        Comment comment = Comment.builder()
                .gallery(galleryService.findGallery(galleryId))
                .member(memberService.findMember(memberId))
                .artworkId(artworkId)
                .content(commentRequestDto.getContent())
                .commentStatus(VALID)
                .build();
        commentRepository.save(comment);
        return new CommentArtworkHeadDto<>(galleryId, artworkId, comment.toCommentArtworkResponseDto());
    }


    private Page<Comment> findCommentByPage(Long galleryId, Long artworkId, int page, int size) {
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
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
        return commentPage;
    }


    public GalleryPageResponseDto<Object> getGalleryCommentPage(Long galleryId, int page, int size, Long memberId){
        memberService.findMember(memberId);
        Page<Comment> commentPage = findCommentByPage(galleryId, null, page, size);
        List<Comment> commentList = commentPage.getContent();
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());
        List<CommentGalleryResDto> response = CommentGalleryResDto.toCommentGalleryResponseDtoList(commentList);
        return new GalleryPageResponseDto<>(galleryId, response, pageInfo);
    }


    public ArtworkPageResponseDto<Object> getArtworkCommentPage(Long galleryId, Long artworkId, int page, int size, Long memberId) {
        memberService.findMember(memberId);
        Page<Comment> commentPage = findCommentByPage(galleryId, artworkId, page, size);
        List<Comment> commentList = commentPage.getContent();
        PageInfo<Object> pageInfo = new PageInfo<>(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());
        List<CommentArtworkResDto> response = CommentArtworkResDto.toCommentArtworkResponseDtoList(commentList);
        return new ArtworkPageResponseDto<>(galleryId, artworkId, response, pageInfo);
    }


    protected Comment findComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        Comment foundComment = comment.orElseThrow(()->new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(foundComment.getCommentStatus() == DELETED) throw new BusinessLogicException(ExceptionCode.COMMENT_DELETED);
        return foundComment;
    }



    public void deleteComment(Long galleryId, Long commentId, Long memberId) {
        Comment foundComment= findComment(commentId);
        checkGalleryCommentVerification(galleryId, commentId, memberId);
        //--검증완료--
        foundComment.changeCommentStatus(DELETED);
    }


    public CommentGalleryHeadDto<Object> modifyComment(Long galleryId, Long commentId, CommentRequestDto commentRequestDto, Long memberId){
        Comment foundComment = findComment(commentId);
        checkGalleryCommentVerification(galleryId, commentId, memberId);
        //--검증완료--
        Comment requestComment = commentRequestDto.toCommentEntity();
        Optional.ofNullable(requestComment.getContent())
                .ifPresent(foundComment::changeContent);
        return new CommentGalleryHeadDto<>(galleryId, foundComment.toCommentGalleryResponseDto());
    }

    private void checkGalleryCommentVerification(Long galleryId, Long commentId, Long memberId) {
        Comment foundComment = findComment(commentId);
        memberService.findMember(memberId);
        galleryService.findGallery(galleryId);
        if (!Objects.equals(galleryId, foundComment.getGallery().getGalleryId())) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND_FROM_GALLERY);
        }
    }
}
