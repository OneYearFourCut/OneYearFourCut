package com.codestates.mainproject.oneyearfourcut.domain.artwork.service;

import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.repository.ArtworkLikeRepository;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.domain.Sort.Order.desc;

@ExtendWith(MockitoExtension.class)
public class ArtworkServiceLocalTest {
    @InjectMocks
    private ArtworkService artworkService;

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private GalleryService galleryService;

    @Mock
    private ArtworkLikeRepository artworkLikeRepository;

    @Nested
    @DisplayName("작품 생성 관련")
    class Create {
        Long galleyId = 1L;
        Long memberId = 2L;
        ArtworkRequestDto artworkRequestDto;

        @BeforeEach
        void setUp() throws IOException {
            MockMultipartFile image = new MockMultipartFile("율무", "율무.jpeg", "image/jpeg", "file".getBytes());
            String title = "제목1";
            String content = "작품에 대한 설명";
            artworkRequestDto = ArtworkRequestDto.builder()
                    .image(image)
                    .title(title)
                    .content(content)
                    .build();
        }

        @Test
        @DisplayName("정상적인 요청")
        void successfulCreateArtwork() {
            Gallery gallery = new Gallery(galleyId);
            Member member = new Member(memberId);
            Artwork artwork = artworkRequestDto.toEntity();

            artwork.setGallery(gallery);
            artwork.setMember(member);

            given(galleryService.findGallery(galleyId)).willReturn(gallery);
            given(artworkRepository.save(any(Artwork.class))).willReturn(artwork);

            artworkService.createArtwork(memberId, galleyId, artworkRequestDto);
        }
    }

    @Nested
    @DisplayName("작품 조회 관련 및 검증 메서드")
    class Read {
        long galleryId = 1L;
        long loginMemberId = 5L;
        Artwork artwork1 = Artwork.builder().artworkId(1L).title("제목1").content("설명1").imagePath("/1.png").build();
        Artwork artwork2 = Artwork.builder().artworkId(2L).title("제목2").content("설명2").imagePath("/2.png").build();
        Artwork artwork3 = Artwork.builder().artworkId(3L).title("제목3").content("설명3").imagePath("/3.png").build();
        Artwork artwork4 = Artwork.builder().artworkId(4L).title("제목4").content("설명4").imagePath("/4.png").build();
        Member loginMember = new Member(loginMemberId);
        Gallery gallery = new Gallery(galleryId);
        ArtworkLike like1 = new ArtworkLike(1L);
        ArtworkLike like2 = new ArtworkLike(2L);
        ArtworkLike like3 = new ArtworkLike(3L);
        ArtworkLike like4 = new ArtworkLike(4L);

        Comment comment1 = Comment.builder().commentId(1L).commentStatus(VALID).artworkId(artwork1.getArtworkId()).build();
        Comment comment2 = Comment.builder().commentId(2L).commentStatus(VALID).artworkId(artwork1.getArtworkId()).build();
        Comment comment3 = Comment.builder().commentId(3L).commentStatus(VALID).artworkId(artwork1.getArtworkId()).build();
        Comment comment4 = Comment.builder().commentId(4L).commentStatus(VALID).artworkId(artwork1.getArtworkId()).build();

        @BeforeEach
        void setUp() {
            artwork1.setGallery(gallery);
            artwork1.setMember(loginMember);
            artwork2.setGallery(gallery);
            artwork2.setMember(loginMember);
            artwork3.setGallery(gallery);
            artwork3.setMember(loginMember);
            artwork4.setGallery(gallery);
            artwork4.setMember(loginMember);

            like1.setArtwork(artwork1);
            like1.setMember(loginMember);
            like2.setArtwork(artwork2);
            like2.setMember(loginMember);
            like3.setArtwork(artwork3);
            like3.setMember(loginMember);
            like4.setArtwork(artwork4);
            like4.setMember(loginMember);
        }


//        @DisplayName("검증 메서드 테스트")
//        @Test
//        void findVerifiedArtworkTest() {
//            Gallery openedGallery = Gallery.builder().galleryId(1L).status(GalleryStatus.OPEN).build();
//            Gallery closedGallery = Gallery.builder().galleryId(2L).status(GalleryStatus.CLOSED).build();
//            Artwork artwork5 = Artwork.builder().artworkId(1L).build();
//            artwork5.setGallery(openedGallery);
//            artwork5.setStatus(ArtworkStatus.REGISTRATION);
//            given(artworkRepository.findById(any(Long.class))).willReturn(Optional.of(artwork5));
//
//            Artwork actualArtwork = ;
//
//            assertThat(artworkService.findVerifiedArtwork(openedGallery.getGalleryId(), artwork5.getArtworkId()))
//                    .is(throw)
//
//        }

        @Nested
        @DisplayName("작품 일괄 조회")
        class FindArtworkList {


            @DisplayName("정상적인 요청")
            @Test
            void successfulFindArtworkListTest() {

                List<Artwork> artworkList = List.of(artwork4, artwork3, artwork2, artwork1);

                given(memberService.findMember(loginMemberId)).willReturn(loginMember);
                given(artworkRepository.findAllByGallery_GalleryIdAndStatus(galleryId, Sort.by(desc("createdAt")), ArtworkStatus.REGISTRATION))
                        .willReturn(artworkList);

                List<ArtworkResponseDto> response = artworkService.findArtworkList(loginMemberId, galleryId);

                assertThat(response.size()).isEqualTo(artworkList.size());
                assertThat(response.get(0).getLikeCount()).isNotNull();
                assertThat(response.get(0).isLiked()).isTrue();
                assertThat(response.get(0).getCommentCount()).isEqualTo(artworkList.get(0).getCommentCount());
            }
        }

        @Nested
        @DisplayName("작품 개별 생성")
        class FindArtwork {

            @Test
            @DisplayName("정상적인 요청")
            void successfulFindArtwork() {
                boolean exceptedLiked = !(artwork1.getArtworkLikeList().isEmpty());

                given(artworkLikeRepository
                        .existsByMember_MemberIdAndArtwork_ArtworkId(loginMemberId, artwork1.getArtworkId()))
                        .willReturn(exceptedLiked);
                given(artworkRepository.findById(any(Long.class))).willReturn(Optional.of(artwork1));

                ArtworkResponseDto actual = artworkService.findArtwork(loginMemberId, galleryId, artwork1.getArtworkId());

                boolean actualLiked = actual.isLiked();

                int actualCommentCount = actual.getCommentCount();
                int expectedCommentCount = artwork1.getCommentCount();

                assertThat(actual).isNotNull();
                assertThat(actualLiked).isEqualTo(exceptedLiked);
                assertThat(actualCommentCount).isEqualTo(expectedCommentCount);
            }
        }
    }
}
