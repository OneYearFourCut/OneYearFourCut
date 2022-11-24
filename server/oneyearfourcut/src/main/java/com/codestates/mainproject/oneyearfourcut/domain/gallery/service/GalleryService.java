package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus.CLOSED;
import static com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus.OPEN;

@Service
@RequiredArgsConstructor
@Transactional
public class GalleryService {
    private final GalleryRepository galleryRepository;
    private final MemberService memberService;

    public GalleryResponseDto createGallery(GalleryRequestDto galleryRequestDto, Long memberId) {
        // 오픈된 전시관이 이미 존재하는지 확인하고 있으면 에러
        verifiedMemberCanOpenGallery(memberId);

        Gallery gallery = galleryRequestDto.toEntity(memberId);

        Gallery savedGallery = galleryRepository.save(gallery);

        return savedGallery.toGalleryResponseDto();
    }

    public GalleryResponseDto modifyGallery(GalleryRequestDto galleryRequestDto, long galleryId, Long loginId) {
        Gallery findGallery = findGallery(galleryId);

        isLoginMemberGallery(findGallery, loginId);

        Optional.ofNullable(galleryRequestDto.getTitle())
                .ifPresent(findGallery::updateTitle);
        Optional.ofNullable(galleryRequestDto.getContent())
                .ifPresent(findGallery::updateContent);

        Gallery savedGallery = galleryRepository.save(findGallery);

        return savedGallery.toGalleryResponseDto();
    }

    @Transactional(readOnly = true)
    public Gallery findGallery(Long galleryId) {
        Optional<Gallery> optionalGallery = galleryRepository.findById(galleryId);

        //전시관이 존재하는지 확인
        Gallery findGallery = optionalGallery.orElseThrow(() -> new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));

        //전시관이 폐관 상태 인지 확인
        if (findGallery.getStatus() == CLOSED) throw new BusinessLogicException(ExceptionCode.CLOSED_GALLERY);

        return findGallery;
    }

    public void deleteGallery(Long galleryId, Long loginId) {
        Gallery findGallery = findGallery(galleryId);

        isLoginMemberGallery(findGallery, loginId);

        findGallery.updateStatus(CLOSED);
    }

    //전시관이 유효한지 검증하는 메서드
    @Transactional(readOnly = true)
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
            galleryList.stream()
                    .forEach(gallery -> {
                        if (gallery.getStatus() == OPEN){
                            throw new BusinessLogicException(ExceptionCode.OPEN_GALLERY_EXIST);
                        }
                    });
        }

    //로그인 회원의 갤러리인지 확인하는 로직
    private void isLoginMemberGallery(Gallery findGallery, Long loginId) {
        if (findGallery.getMember().getMemberId() != loginId) {
            throw new BusinessLogicException(ExceptionCode.NO_AUTHORITY);
        }
    }
}
