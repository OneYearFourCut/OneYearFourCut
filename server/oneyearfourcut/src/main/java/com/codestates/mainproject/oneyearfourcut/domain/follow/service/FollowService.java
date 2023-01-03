package com.codestates.mainproject.oneyearfourcut.domain.follow.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEventPublisher;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.follow.dto.FollowerResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.follow.dto.FollowingResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.follow.entity.Follow;
import com.codestates.mainproject.oneyearfourcut.domain.follow.repository.FollowRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import com.codestates.mainproject.oneyearfourcut.global.page.ReplyListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final GalleryService galleryService;
    private final AlarmEventPublisher alarmEventPublisher;

    @Transactional
    public Follow createFollow(Long loginMemberId, Long targetGalleryId) {
        Member loginMember = memberService.findMember(loginMemberId);
        galleryService.verifiedGalleryExist(targetGalleryId);
        Gallery targetGallery = galleryService.findGallery(targetGalleryId);

        Long galleryOwnerMemberId = targetGallery.getMember().getMemberId();

        if(Objects.equals( loginMemberId, galleryOwnerMemberId )){
            throw new BusinessLogicException(ExceptionCode.CANNOT_FOLLOW_OWN_GALLERY);
        }
        if(followRepository.existsByMember_MemberIdAndFollowMemberId(loginMemberId, galleryOwnerMemberId )){
            throw new BusinessLogicException(ExceptionCode.ALREADY_FOLLOWED);
        }

        Boolean isFollowingMeCheck = followRepository.existsByMember_MemberIdAndFollowMemberId(galleryOwnerMemberId, loginMemberId) ;
        //반대로 , 갤러리유저가 나를 팔로우 하는가? true or false

        if(isFollowingMeCheck){
            try {Follow follow = Follow.builder()
                    .member(loginMember)
                    .followMemberId(galleryOwnerMemberId)
                    .gallery(targetGallery)
                    .isFollowTogetherCheck(true)
                    .build();
            alarmEventPublisher.publishAlarmEvent(followRepository.save(follow).toAlarmEvent(galleryOwnerMemberId));
            //알람생성
            return follow;
            }
            finally{
                Follow foundOppositeFollow = findVerifiedFollowByMemberAndGallery(
                        targetGallery.getMember(), galleryService.findLoginGallery(loginMemberId));
                //내 갤러리를 follow 했던 유저의 follow Id 의 following me check를 true 값으로 변환 (맞팔)
                foundOppositeFollow.changeFollowTogetherCheck(true);
            }
        }
        else{
            Follow follow = Follow.builder()
                    .member(loginMember)
                    .followMemberId(galleryOwnerMemberId)
                    .gallery(targetGallery)
                    .isFollowTogetherCheck(false)
                    .build();
            alarmEventPublisher.publishAlarmEvent(followRepository.save(follow).toAlarmEvent(galleryOwnerMemberId));
            // 알람 생성
            return follow;
        }

    }

    @Transactional
    public Boolean unfollow(Long myMemberId, Long otherGalleryId) {
        Member myMember = memberService.findMember(myMemberId);
        Gallery otherGallery = galleryService.findGallery(otherGalleryId);
        Follow foundMyFollowing = findVerifiedFollowByMemberAndGallery(myMember, otherGallery);

        // 반대쪽에서도 팔로잉되어 있는 상태일때 or 맞팔상태일때 : (other) true, true (me) ->  (other) false , deleted (me)
        if(foundMyFollowing.getIsFollowTogetherCheck()){
            Follow foundOppositeFollow = findVerifiedFollowByMemberAndGallery(
                    memberService.findMember( foundMyFollowing.getFollowMemberId() ) ,
                    galleryService.findLoginGallery(myMemberId) );
            // 검증 성공시 다음 로직 실행
            foundOppositeFollow.changeFollowTogetherCheck(false);
        }

        followRepository.delete(foundMyFollowing);
        return true;
    }

    @Transactional
    public Boolean deleteFollower(Long myMemberId, Long otherMemberId) {
        memberService.findMember(myMemberId);
        Member otherMember = memberService.findMember(otherMemberId);
        Gallery myGallery = galleryService.findLoginGallery(myMemberId);

        Follow foundOtherFollowing =
                followRepository.findByFollowMemberIdAndMemberAndGallery(myMemberId, otherMember, myGallery)
                        .orElseThrow(
                                () -> new BusinessLogicException(ExceptionCode.FOLLOW_NOT_FOUND)
                        );

        // 내가 팔로잉 하고 있을떄 or 맞팔 상태일때 : (other) true, true (me) -> (other) deleted, false (me)
        if(foundOtherFollowing.getIsFollowTogetherCheck()){
            Follow foundMyFollow = findVerifiedFollowByMemberAndGallery(
                    memberService.findMember( otherMemberId ) ,
                    galleryService.findLoginGallery( myMemberId ) );
            // 검증 성공시 다음 로직 실행
            foundMyFollow.changeFollowTogetherCheck(false);
        }

        followRepository.delete(foundOtherFollowing);

        return true;
    }

    private Follow findVerifiedFollowByMemberAndGallery(Member member, Gallery gallery) {
        return followRepository.findByMemberAndGallery(member, gallery).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.FOLLOW_NOT_FOUND_FROM_GALLERY));
    }

    @Transactional(readOnly = true)  //내 팔로잉 리스트를 불러온다.
    public List<FollowingResponseDto> getFollowingListByMemberId(Long memberId) {
        List<Follow> followingList = followRepository.findAllByMember_MemberIdAndGallery_StatusOrderByFollowIdDesc(memberId, GalleryStatus.OPEN);
        return FollowingResponseDto.toFollowingResponseDtoList(followingList);
    }

    @Transactional(readOnly = true) //내 팔로워 리스트를 불러온다.
    public List<FollowerResponseDto> getFollowerListByMemberId(Long memberId) {
        List<Follow> followerList = followRepository.findAllFollowerListByMemberId(memberId);
        return FollowerResponseDto.toFollowerResponseDtoList(followerList);
    }

}

