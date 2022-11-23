package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus.CLOSED;
import static com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus.OPEN;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;
    private final MemberService memberService;

    public Gallery createGallery(Gallery requestGallery, Long memberId) {
        // 오픈된 전시관이 이미 존재하는지 확인하고 있으면 에러
        verifiedMemberCanOpenGallery(memberId);

        //초기상태가 open이므로 넣어줘야함
        requestGallery.setStatus(OPEN);

        Member member = new Member();
        member.setMemberId(memberId);
        requestGallery.setMember(member);

        return galleryRepository.save(requestGallery);
    }

    public Gallery modifyGallery(Gallery requestGallery, Gallery findGallery) {
        Optional.ofNullable(requestGallery.getTitle())
                .ifPresent(findGallery::setTitle);
        Optional.ofNullable(requestGallery.getContent())
                .ifPresent(findGallery::setContent);

        return galleryRepository.save(findGallery);
    }

    public Gallery findGallery(Long galleryId) {
        Optional<Gallery> optionalGallery = galleryRepository.findById(galleryId);

        //전시관이 존재하는지 확인
        Gallery findGallery = optionalGallery.orElseThrow(() -> new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));

        //전시관이 폐관 상태 인지 확인
        if (findGallery.getStatus() == CLOSED) throw new BusinessLogicException(ExceptionCode.CLOSED_GALLERY);

        return findGallery;
    }

    public void deleteGallery(Long galleryId) {
        Gallery gallery = findGallery(galleryId);
        gallery.setStatus(CLOSED);

        galleryRepository.save(gallery);
    }

    //전시관이 유효한지 검증하는 메서드
    public void verifiedGalleryExist(Long galleryId) {
        Optional<Gallery> optionalGallery = galleryRepository.findById(galleryId);

        //전시관이 존재하는지 확인
        Gallery findGallery = optionalGallery.orElseThrow(() -> new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));

        //전시관이 폐관 상태 인지 확인
        if (findGallery.getStatus() == CLOSED) throw new BusinessLogicException(ExceptionCode.CLOSED_GALLERY);
    }


    //유저가 전시관을 열 수 있는지 확인하는 메서드(이미 오픈된 전시관을 가지고 있으면 에러)
    private void verifiedMemberCanOpenGallery(Long memberId) {
        Member loginMember = memberService.findMember(memberId);
        List<Gallery> galleryList = loginMember.getGalleryList();
        if (Optional.ofNullable(galleryList).isPresent()) {
            galleryList.stream()
                    .forEach(gallery -> {
                        if (gallery.getStatus() == OPEN){
                            throw new BusinessLogicException(ExceptionCode.OPEN_GALLERY_EXIST);
                        }
                    });
        }
    }
}
