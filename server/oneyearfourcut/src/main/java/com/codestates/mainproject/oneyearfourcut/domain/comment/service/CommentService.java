package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentType;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.DELETED;
import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;
import static org.springframework.data.domain.Sort.Order.desc;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final GalleryRepository galleryRepository;
    private final ArtworkRepository artworkRepository;

    private final MemberService memberService;
    private final GalleryService galleryService;
    private final ArtworkService artworkService;

    //댓글 생성 메소드, 1)galleryservice 통해서 회원/갤러리검증, 2)댓글VALID설정 3)memberid, gallery set(양쪽) 4)artwork set 5)save
    public void createComment(Comment comment, Long galleryId, Long artworkId, Long memberId) {
        galleryService.verifiedGalleryExist(memberId);
        comment.setCommentStatus(VALID);
        Member member = new Member();
        member.setMemberId(memberId);
        comment.setMember(member);
        comment.setGallery(galleryService.findGallery(galleryId));
        if(artworkId != null){
            comment.setArtworkId(artworkId);
        }
        commentRepository.save(comment);
    }

    //find & Pagination method
    public Page<Comment> findCommentByPage(Long galleryId, Long artworkId, int page, int size) {
        PageRequest pr = PageRequest.of(page - 1, size);
        Page<Comment> commentPage;
        galleryService.findGallery(galleryId);
        if (artworkId == null) {
            commentPage =
                    commentRepository.findAllByCommentStatusAndGallery_GalleryIdOrderByCommentIdDesc(VALID,galleryId, pr);
        }
        else {
            commentPage = commentRepository.findAllByCommentStatusAndArtworkIdOrderByCommentIdDesc(VALID, artworkId, pr);
        }
        if (commentPage.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
        return commentPage;
    }


    //comment jpa레포 존재여부 검증 메소드
    public Comment findComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        Comment foundComment = comment.orElseThrow(()->new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(foundComment.getCommentStatus() == DELETED) throw new BusinessLogicException(ExceptionCode.COMMENT_DELETED);
        return foundComment;
    }

    //comment 삭제 메소드, 1)pathvariable를 통해서 gallery존재 확인, 2)repo존재 확인 3)status deleted 4)save
    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
        comment.setCommentStatus(DELETED);
        commentRepository.save(comment);
    }

    //comment update
    public Comment modifyComment(Comment reqeustComment, Comment foundComment){
        Optional.ofNullable(reqeustComment.getContent())
                .ifPresent(foundComment::setContent);
        return commentRepository.save(foundComment);
    }


}
