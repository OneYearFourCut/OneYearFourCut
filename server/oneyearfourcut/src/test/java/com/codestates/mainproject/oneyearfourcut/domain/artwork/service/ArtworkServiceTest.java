package com.codestates.mainproject.oneyearfourcut.domain.artwork.service;

import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.LikeStatus;
import com.codestates.mainproject.oneyearfourcut.domain.Like.repository.ArtworkLikeRepository;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.OneYearFourCutResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.aws.service.AwsS3Service;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTest {
    @InjectMocks
    private ArtworkService artworkService;

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private AwsS3Service awsS3Service;

    @Mock
    private MemberService memberService;

    @Mock
    private GalleryService galleryService;

    @Mock
    private ArtworkLikeRepository artworkLikeRepository;

    @Mock
    private AlarmService alarmService;

    @Nested
    @DisplayName("파일 업로드 관련 - 작품 등록 및 수정")
    class CreateUpdate {
        Member writer;
        Member loginMember;
        Gallery gallery;
        ArtworkPostDto artworkPostDto;
        ArtworkPatchDto artworkPatchDto;

        @BeforeEach
        public void setUp() {
            writer = new Member(1L);
            loginMember = new Member(2L);
            gallery = new Gallery(1L);

            MockMultipartFile image = new MockMultipartFile("테스트", "테스트.jpeg", "image/jpeg", "file".getBytes());
            String title = "테스트";
            String content = "테스트";

            artworkPostDto = ArtworkPostDto.builder()
                    .image(image)
                    .title(title)
                    .content(content)
                    .build();

            artworkPatchDto = ArtworkPatchDto.builder()
                    .image(image)
                    .title(title)
                    .content(content).build();
        }

        @Nested
        @DisplayName("작품 생성 관련")
        class Create {
            @Test
            @DisplayName("정상적인 요청")
            public void createArtworkTest() {
                Artwork artwork = new Artwork(1L);
                Artwork change = artworkPostDto.toEntity();
                artwork.modify(change);
                String imagePath = "uuid";

                artwork.setMember(writer);
                artwork.setGallery(gallery);
                artwork.setImagePath(imagePath);

                willDoNothing().given(alarmService).createAlarmBasedOnArtwork(any(), any(), any());
                willDoNothing().given(galleryService).verifiedGalleryExist(any(Long.class));
                given(awsS3Service.uploadFile(any())).willReturn(imagePath);
                given(artworkRepository.save(any(Artwork.class))).willReturn(artwork);

                ArtworkResponseDto createdArtwork = artworkService.createArtwork(writer.getMemberId(), gallery.getGalleryId(), artworkPostDto);

                assertThat(createdArtwork.getTitle()).isEqualTo(artworkPostDto.getTitle());
                assertThat(createdArtwork.getContent()).isEqualTo(artworkPostDto.getContent());
                assertThat(createdArtwork.getImagePath()).isEqualTo(imagePath);
            }
        }

        @Nested
        @DisplayName("작품 수정 관련")
        class Update {
            @Test
            public void updateArtworkTest() {

                Artwork artwork = new Artwork(1L);
                artwork.setMember(writer);
                gallery.setMember(loginMember);
                artwork.setGallery(gallery);

                String imagePath = "/image.jpg";

                willDoNothing().given(galleryService).verifiedGalleryExist(any());
                given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));
                given(awsS3Service.uploadFile(any())).willReturn(imagePath);
                willDoNothing().given(awsS3Service).deleteImage(any());

                ArtworkResponseDto result =
                        artworkService.updateArtwork(writer.getMemberId(), gallery.getGalleryId(), artwork.getArtworkId(), artworkPatchDto);

                assertThat(result.getArtworkId()).isEqualTo(artwork.getArtworkId());
                assertThat(result.getImagePath()).isEqualTo(imagePath);
                assertThat(result.getTitle()).isEqualTo(artworkPatchDto.getTitle());
                assertThat(result.getContent()).isEqualTo(artworkPatchDto.getContent());
            }
        }
    }

    @Nested
    @DisplayName("작품 조회 관련")
    class Read {
        Member writer;
        Member loginMember;
        Member nonLoginMember;

        Gallery gallery;

        Artwork artwork1;
        Artwork artwork2;
        Artwork artwork3;
        Artwork artwork4;
        Artwork artwork5;
        List<Artwork> artworkList;
        List<Artwork> oneYearFourCut;


        @BeforeEach
        public void setup() {
            writer = new Member(1L);
            loginMember = new Member(2L);
            nonLoginMember = new Member(3L);
            gallery = new Gallery(1L);

            artwork1 = new Artwork(1L, 32);
            artwork1.setGallery(gallery);
            artwork1.setMember(writer);
            artwork2 = new Artwork(2L, 23);
            artwork2.setGallery(gallery);
            artwork2.setMember(writer);
            artwork3 = new Artwork(3L, 42);
            artwork3.setGallery(gallery);
            artwork3.setMember(writer);
            artwork4 = new Artwork(4L, 12);
            artwork4.setGallery(gallery);
            artwork4.setMember(writer);
            artwork5 = new Artwork(5L, 3);
            artwork5.setGallery(gallery);
            artwork5.setMember(writer);

            artworkList = List.of(artwork1, artwork2, artwork3, artwork4, artwork5);
            oneYearFourCut = List.of(artwork3, artwork1, artwork2, artwork4);
        }

        @Nested
        @DisplayName("작품 개별 조회")
        class FindArtworkTest {

            @Test
            @DisplayName("좋아요를 누른 유저 개별 조회 테스트")
            void findArtworkWithLikeTest() {
                ArtworkLike like = new ArtworkLike(1L);
                like.setArtwork(artwork1);
                like.setMember(loginMember);

                willDoNothing().given(galleryService).verifiedGalleryExist(any());
                given(artworkRepository.findById(any(Long.class))).willReturn(Optional.of(artwork1));

                given(artworkLikeRepository
                        .existsByMember_MemberIdAndArtwork_ArtworkIdAndStatus(loginMember.getMemberId(), artwork1.getArtworkId(), LikeStatus.LIKE))
                        .willReturn(true);

                ArtworkResponseDto response = artworkService.findArtwork(loginMember.getMemberId(), gallery.getGalleryId(), artwork1.getArtworkId());
                assertThat(response.isLiked()).isEqualTo(artwork1.isLiked());

            }

            @Test
            @DisplayName("좋아요를 누르지 않은 유저 개별 조회 테스트")
            void findArtworkWithoutLikeTest() {
                ArtworkLike like = new ArtworkLike(1L);
                like.setArtwork(artwork1);
                like.setMember(loginMember);

                willDoNothing().given(galleryService).verifiedGalleryExist(any());
                given(artworkRepository.findById(any(Long.class))).willReturn(Optional.of(artwork1));

                given(artworkLikeRepository
                        .existsByMember_MemberIdAndArtwork_ArtworkIdAndStatus(any(Long.class), any(Long.class), eq(LikeStatus.LIKE)))
                        .willReturn(false);

                ArtworkResponseDto likeResponse = artworkService.findArtwork(nonLoginMember.getMemberId(), gallery.getGalleryId(), artwork1.getArtworkId());
                assertThat(likeResponse.isLiked()).isEqualTo(false);

            }

        }

        @Nested
        @DisplayName("작품 일괄 조회")
        class FindArtworkListTest {

            @Test
            @DisplayName("특정 작품에 좋아요를 누른 유저 일괄 조회 테스트")
            public void findArtworkListByLoginUser() {
                ArtworkLike like = new ArtworkLike(1L);
                like.setMember(loginMember);
                like.setArtwork(artwork1);

                willDoNothing().given(galleryService).verifiedGalleryExist(any(Long.class));
                given(artworkRepository.findAllByGallery_GalleryIdAndStatus(any(Long.class), any(ArtworkStatus.class), any(Sort.class)))
                        .willReturn(artworkList);
                given(memberService.findMember(any())).willReturn(loginMember);

                List<ArtworkResponseDto> artworks = artworkService.findArtworkList(loginMember.getMemberId(), gallery.getGalleryId());
                assertThat(artworks.size()).isEqualTo(artworkList.size());
                assertThat(artworks).extracting("liked").contains(true);
            }

            @Test
            @DisplayName("비로그인 유저 일괄 조회 테스트")
            public void successfulFindArtworkListByNonLoginUser() {
                ArtworkLike like = new ArtworkLike(1L);
                like.setMember(loginMember);
                like.setArtwork(artwork1);

                willDoNothing().given(galleryService).verifiedGalleryExist(any(Long.class));
                given(artworkRepository.findAllByGallery_GalleryIdAndStatus(any(Long.class), any(ArtworkStatus.class), any(Sort.class)))
                        .willReturn(artworkList);
                given(memberService.findMember(any())).willReturn(nonLoginMember);

                List<ArtworkResponseDto> artworks = artworkService.findArtworkList(nonLoginMember.getMemberId(), gallery.getGalleryId());
                assertThat(artworks.size()).isEqualTo(artworkList.size());
                assertThat(artworks).extracting("liked").doesNotContain(true);
            }
        }

        @Nested
        @DisplayName("올해 네 컷 조회")
        class FindOneYearFourCut {

            @Test
            @DisplayName("정상적으로 요청한 올해 네 컷 조회")
            public void findOneYearFourCutTest() {

                willDoNothing().given(galleryService).verifiedGalleryExist(any());
                given(artworkRepository.findTop4ByGallery_GalleryIdAndStatus(any(), any(), any()))
                        .willReturn(oneYearFourCut);

                List<OneYearFourCutResponseDto> result = artworkService.findOneYearFourCut(gallery.getGalleryId());

                assertThat(result.size()).isLessThanOrEqualTo(4);
                assertThat(result.get(0).getArtworkId()).isEqualTo(oneYearFourCut.get(0).getArtworkId());
                assertThat(result.get(1).getArtworkId()).isEqualTo(oneYearFourCut.get(1).getArtworkId());
                assertThat(result.get(2).getArtworkId()).isEqualTo(oneYearFourCut.get(2).getArtworkId());
                assertThat(result.get(3).getArtworkId()).isEqualTo(oneYearFourCut.get(3).getArtworkId());
            }
        }

    }


    @Nested
    @DisplayName("작품 삭제 관련")
    class Delete {

        Member loginMember;
        Member adminMember;
        Member member;
        Gallery gallery;
        Artwork artwork;

        @BeforeEach
        public void setup() {
            adminMember = new Member(1L);
            loginMember = new Member(2L);
            member = new Member(3L);
            gallery = new Gallery(1L);
            gallery.setMember(adminMember);
            artwork = new Artwork(1L);
            artwork.setGallery(gallery);
            artwork.setMember(loginMember);
        }

        @Test
        @DisplayName("작성자가 작품 삭제")
        public void DeleteArtworkByWriterTest() {
            artwork.setMember(loginMember);

            willDoNothing().given(galleryService).verifiedGalleryExist(any());
            given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));

            artworkService.deleteArtwork(
                    loginMember.getMemberId(),
                    gallery.getGalleryId(),
                    artwork.getArtworkId());

            assertThat(artwork.getStatus()).isEqualTo(ArtworkStatus.DELETED);
        }

        @Test
        @DisplayName("갤러리 주인이 작품 삭제")
        public void DeleteArtworkByAdminTest() {

            willDoNothing().given(galleryService).verifiedGalleryExist(any());
            given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));

            artworkService.deleteArtwork(
                    adminMember.getMemberId(),
                    gallery.getGalleryId(),
                    artwork.getArtworkId());

            assertThat(artwork.getStatus()).isEqualTo(ArtworkStatus.DELETED);
        }

        @Test
        @DisplayName("관계 없는 유저가 작품 삭제")
        public void DeleteArtworkByUserTest() {

            willDoNothing().given(galleryService).verifiedGalleryExist(any());
            given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));

            assertThatCode(() -> {
                artworkService.deleteArtwork(4L, gallery.getGalleryId(), artwork.getArtworkId());
                    }
            ).isInstanceOf(BusinessLogicException.class).hasMessage(ExceptionCode.UNAUTHORIZED.getMessage());

        }
    }

    @Nested
    @DisplayName("검증 관련")
    class Verification {

        Member admin;
        Member writer;
        Gallery gallery;
        Artwork artwork;

        @BeforeEach
        public void setup() {
            admin = new Member(1L);
            writer = new Member(2L);

            gallery = new Gallery(1L);
            gallery.setMember(admin);

            artwork = new Artwork(1L);
            artwork.setGallery(gallery);
            artwork.setMember(writer);
        }

        @Nested
        @DisplayName("FindVerifiedArtwork 테스트")
        class FindVerifiedArtworkTest {
            @Test
            @DisplayName("정상적으로 요청하였을 경우")
            public void successfulFindVerifiedArtworkTest() {
                given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));

                assertThatCode(() -> {
                    artworkService.findVerifiedArtwork(gallery.getGalleryId(), artwork.getArtworkId());
                }).doesNotThrowAnyException();
            }

            @Test
            @DisplayName("존재하지 않는 artworkId로 작품을 찾을 경우")
            public void unsuccessfulFindVerifiedArtworkTest1() {

                given(artworkRepository.findById(any())).willReturn(Optional.empty());

                assertThatCode(() -> {
                    artworkService.findVerifiedArtwork(gallery.getGalleryId(), 3L);
                }).isInstanceOf(BusinessLogicException.class).hasMessage(ExceptionCode.ARTWORK_NOT_FOUND.getMessage());

            }

            @Test
            @DisplayName("존재하지 않는 galleryId로 작품을 찾을 경우")
            public void unsuccessfulFindVerifiedArtworkTest2() {

                given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));

                assertThatCode(() -> {
                    artworkService.findVerifiedArtwork(3L, artwork.getArtworkId());
                }).isInstanceOf(BusinessLogicException.class).hasMessage(ExceptionCode.ARTWORK_NOT_FOUND_FROM_GALLERY.getMessage());
            }
        }

        @Nested
        @DisplayName("checkGalleryArtworkVerification 테스트")
        class checkGalleryArtworkVerificationTest {
            @Test
            @DisplayName("정상적으로 요청하였을 경우")
            public void successfulFindVerifiedArtworkTest() {
                given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));

                assertThatCode(() -> {
                    artworkService.checkGalleryArtworkVerification(gallery.getGalleryId(), artwork.getArtworkId());
                }).doesNotThrowAnyException();
            }

            @Test
            @DisplayName("존재하지 않는 artworkId로 작품을 찾을 경우")
            public void unsuccessfulFindVerifiedArtworkTest1() {

                given(artworkRepository.findById(any())).willReturn(Optional.empty());

                assertThatCode(() -> {
                    artworkService.checkGalleryArtworkVerification(gallery.getGalleryId(), 3L);
                }).isInstanceOf(BusinessLogicException.class).hasMessage(ExceptionCode.ARTWORK_NOT_FOUND.getMessage());

            }

            @Test
            @DisplayName("존재하지 않는 galleryId로 작품을 찾을 경우")
            public void unsuccessfulFindVerifiedArtworkTest2() {

                given(artworkRepository.findById(any())).willReturn(Optional.of(artwork));

                assertThatCode(() -> {
                    artworkService.checkGalleryArtworkVerification(3L, artwork.getArtworkId());
                }).isInstanceOf(BusinessLogicException.class).hasMessage(ExceptionCode.ARTWORK_NOT_FOUND_FROM_GALLERY.getMessage());
            }
        }
    }
}



