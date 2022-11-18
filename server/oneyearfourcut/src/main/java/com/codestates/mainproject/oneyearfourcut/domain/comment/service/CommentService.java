package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.asc;
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

    @Transactional //comment on gallery
    public Comment createGalleryComment(Comment comment,Long galleryId,Long memberId){
        /*Member member = memberService.findMember(comment.getMember().getMemberId()); //해당 memberId 존재 확인, JWT*/
        /*comment.setGallery(galleryService.findGallery(galleryId)); //gallerId를찾아 comment 생성*/
        Optional<Member> tempMember = memberRepository.findById(memberId);
        Optional<Gallery> tempGallery = galleryRepository.findById(galleryId);

        Member member = tempMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Gallery gallery = tempGallery.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));

        comment.setGallery(gallery);
        comment.setMember(member);  //Member에 저장.
        return commentRepository.save(comment);
    }
    @Transactional //comment on artwork
    public Comment createArtworkComment(Comment comment,Long galleryId, Long artworkId, Long memberId){
        /*Member member = memberService.findMember(comment.getMember().getMemberId());
        comment.setGallery(galleryService.findGallery(galleryId));*/
        Optional<Member> tempMember = memberRepository.findById(memberId);
        Optional<Gallery> tempGallery = galleryRepository.findById(galleryId);

        Member member = tempMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Gallery gallery = tempGallery.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));

        comment.setGallery(gallery);
        comment.setMember(member);
        comment.setArtworkId(artworkId); //comment db에 저장.
        return commentRepository.save(comment); //save
    }

    @Transactional
    public List<Comment> findCommentOnGallery(Long galleryId){
        List<Comment> commentList1 =
                commentRepository.findAllByGallery_GalleryId(galleryId, Sort.by(desc("createdAt")));
        if(commentList1.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND);
        }
        return commentList1;
    }

    @Transactional
    public List<Comment> findCommentOnArtwork(Long artworkId){
        List<Comment> commentList2 =
                commentRepository.findAllByArtworkId(artworkId,Sort.by(desc("createdAt")));
        if(commentList2.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND);
        }
        return commentList2;
    }


 /*   //Update method
    public Comment updateComment(Comment comment, Long memberId){
        Comment foundComment = findComment(comment.getCommentId());

        //member 검증. JWT 수정 필요.
        Long foundMember = mService.findMember(comment.getMember().getMemberId()).getMemberId();
        if(Objects.equals(memberId, foundMember)){

            Optional.ofNullable(comment.getContent())
                    .ifPresent(foundComment::setContent);

        }
        return cRepo.save(comment);
    }

    //Delete method
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = findComment(commentId);
        //member 검증. JWT 수정 필요.
        Long foundMember = mService.findMember(comment.getMember().getMemberId()).getMemberId();
        if (Objects.equals(memberId, foundMember)) {//member검증. JWT 수정 필요.
            cRepo.delete(comment);
        }
    }


    //Pagination method
    public Page<Comment> pageComments(int page, int size){
        PageRequest pr = PageRequest.of(page -1, size);
        return  cRepo.findAllByOrderByCreatedDateAsc(pr);
    }*/



}
