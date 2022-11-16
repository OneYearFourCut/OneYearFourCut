package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository cRepo;
    private final MemberRepository mRepo;
    private final GalleryRepository gRepo;
    private final ArtworkRepository aRepo;

    private final MemberService mService;
    private final GalleryService gService;
    private final ArtworkService aService;



    //Create method
    public Comment createComment(Comment comment, Long galleryId){

        Member member = mService.findMember(comment.getMember().getMemberId()); //해당 memberId 존재 확인
        comment.setGallery(gService.findGallery(galleryId));  //gallerId를찾아 comment 생성
        comment.setMember(member);
        return cRepo.save(comment);
    }

    //Read(find) method
    public Comment findComment(Long commentId){
        Optional<Comment> comment = cRepo.findById(commentId);
        if(comment.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
        return comment.get();
    }

/*    //Update method
    public Comment updateComment(Comment comment, Long memberId){
        Comment foundComment =
        return cRepo.save(comment);
    }

    //Delete method
    public void deleteComment(Long commentId){

        cRepo.delete()
    }

    private void verifiedMember(Comment comment){
        mService.findMember(comment.getMember().getMemberId);
    }

    private void verifiedGallery(Comment comment){
        gService.findGallery(comment.getGallery().getGalleryId);
    }*/

    //Pagination method
    public Page<Comment> pageComments(int page, int size){
        PageRequest pr = PageRequest.of(page -1, size);
        return  cRepo.findAllByOrderByCommentIdDesc(pr);
    }




}
