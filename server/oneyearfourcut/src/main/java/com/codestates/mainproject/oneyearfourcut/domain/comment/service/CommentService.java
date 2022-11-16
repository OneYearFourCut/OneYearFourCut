package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtWorkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.CommentRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository cRepo;
    private final MemberRepository mRepo;
    private final GalleryRepository gRepo;
    private final ArtWorkRepository aRepo;

    private final MemberService mService;
    private final GalleryService gService;
    private final ArtWorkService aService;


/*    //Create method
    public Comment createComment(Comment comment, Long memberId){
        comment.setMember(mService.findMember(memberId));
        verifiedMember(comment);
        comment.setMember(mService.getNickname(memberId));
        comment.setGallery(gService.findGallery(comment.getGallery().getGalleryId()));
        verifiedGallery(comment);
        return cRepo.save(comment);
    }

    //Read method
    public Comment findComment(Long commentId){
        return findVer
    }

    //Update method
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
