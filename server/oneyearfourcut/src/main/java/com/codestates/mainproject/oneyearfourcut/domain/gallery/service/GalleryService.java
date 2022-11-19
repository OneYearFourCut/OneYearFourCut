package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
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

    public Gallery findGallery(Long galleryId) {
        Optional<Gallery> optionalGallery = galleryRepository.findById(galleryId);

        //전시관이 존재하는지 확인
        Gallery findGallery = optionalGallery.orElseThrow(() -> new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));

        //전시관이 폐관 상태 인지 확인
        if (findGallery.getStatus() == CLOSED) throw new BusinessLogicException(ExceptionCode.CLOSED_GALLERY);

        return findGallery;
    }

    public void verifiedGalleryExist(Long memberId) {
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

    public Gallery createGallery(Gallery postGallery) {
        return galleryRepository.save(postGallery);
    }
}
