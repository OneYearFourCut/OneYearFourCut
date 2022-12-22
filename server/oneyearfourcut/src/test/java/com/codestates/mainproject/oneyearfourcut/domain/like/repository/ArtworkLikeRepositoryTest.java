package com.codestates.mainproject.oneyearfourcut.domain.like.repository;

import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.LikeStatus;
import com.codestates.mainproject.oneyearfourcut.domain.Like.repository.ArtworkLikeRepository;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArtworkLikeRepositoryTest {

    @Autowired
    private ArtworkLikeRepository artworkLikeRepository;
    @Autowired
    private ArtworkRepository artworkRepository;

    @DisplayName("쿼리 메서드 관련")
    @Nested
    class QueryMethodTest {

        @Test
        @DisplayName("member와 artwork에 해당하는 like 리턴 테스트")
        public void findByMemberAndArtwork() {
            ArtworkLike like1 = new ArtworkLike(1L);
            like1.setArtwork(new Artwork(1L));
            like1.setMember(new Member(1L));
            artworkLikeRepository.save(like1);
            ArtworkLike like2 = new ArtworkLike(2L);
            like2.setArtwork(new Artwork(2L));
            like2.setMember(new Member(2L));
            artworkLikeRepository.save(like2);
            ArtworkLike like3 = new ArtworkLike(3L);
            like3.setArtwork(new Artwork(3L));
            like3.setMember(new Member(3L));
            artworkLikeRepository.save(like3);

            Optional<ArtworkLike> actual1 = artworkLikeRepository.findByMemberAndArtwork(
                    new Member(2L), new Artwork(2L));
            Optional<ArtworkLike> actual2 = artworkLikeRepository.findByMemberAndArtwork(
                    new Member(1L), new Artwork(2L));
            Optional<ArtworkLike> actual3 = artworkLikeRepository.findByMemberAndArtwork(
                    new Member(2L), new Artwork(1L));

            assertThat(actual1).isNotEmpty();
            assertThat(actual1.get().getArtworkLikeId()).isEqualTo(like2.getArtworkLikeId());
            assertThat(actual1.get().getArtwork().getArtworkId()).isEqualTo(like2.getArtwork().getArtworkId());
            assertThat(actual1.get().getMember().getMemberId()).isEqualTo(like2.getMember().getMemberId());
            assertThat(actual2).isEmpty();
            assertThat(actual3).isEmpty();
        }


        @Test
        @DisplayName("memberId - artworkId에 해당하는 like 존재 테스트")
        public void existsLikeTest() {
            ArtworkLike like1 = new ArtworkLike(1L);
            like1.setArtwork(new Artwork(1L));
            like1.setMember(new Member(1L));
            artworkLikeRepository.save(like1);
            ArtworkLike like2 = new ArtworkLike(2L);
            like2.setArtwork(new Artwork(2L));
            like2.setMember(new Member(2L));
            artworkLikeRepository.save(like2);
            ArtworkLike like3 = new ArtworkLike(3L);
            like3.setArtwork(new Artwork(3L));
            like3.setMember(new Member(3L));
            artworkLikeRepository.save(like3);

            boolean actual1 = artworkLikeRepository
                    .existsByMember_MemberIdAndArtwork_ArtworkIdAndStatus(1L, 1L, LikeStatus.LIKE);
            boolean actual2 = artworkLikeRepository
                    .existsByMember_MemberIdAndArtwork_ArtworkIdAndStatus(1L, 2L, LikeStatus.LIKE);
            boolean actual3 = artworkLikeRepository
                    .existsByMember_MemberIdAndArtwork_ArtworkIdAndStatus(2L, 1L, LikeStatus.LIKE);

            assertThat(actual1).isEqualTo(true);
            assertThat(actual2).isEqualTo(false);
            assertThat(actual3).isEqualTo(false);
        }
    }

    @Test
    @DisplayName("FK artworkId를 가진 데이터 삭제 상태 변경 테스트")
    public void deleteByArtworkId() {
        Artwork artwork1 = Artwork.builder().title("작품1").content("설명1").build();
        artwork1.setImagePath("/test1.jpg");
        artworkRepository.save(artwork1);
        Artwork artwork2 = Artwork.builder().title("작품2").content("설명2").build();
        artwork2.setImagePath("/test2.jpg");
        artworkRepository.save(artwork2);

        ArtworkLike like1 = new ArtworkLike();
        like1.setArtwork(artwork1);
        artworkLikeRepository.save(like1);
        ArtworkLike like2 = new ArtworkLike();
        like2.setArtwork(artwork2);
        artworkLikeRepository.save(like2);
        ArtworkLike like3 = new ArtworkLike();
        like3.setArtwork(artwork2);
        artworkLikeRepository.save(like3);
        ArtworkLike like4 = new ArtworkLike();
        like4.setArtwork(artwork2);
        artworkLikeRepository.save(like4);

        artworkLikeRepository.deleteByArtworkId(artwork2.getArtworkId());
        ArtworkLike findLike1 = artworkLikeRepository.findById(like1.getArtworkLikeId()).get();
        ArtworkLike findLike2 = artworkLikeRepository.findById(like2.getArtworkLikeId()).get();
        ArtworkLike findLike3 = artworkLikeRepository.findById(like3.getArtworkLikeId()).get();
        ArtworkLike findLike4 = artworkLikeRepository.findById(like4.getArtworkLikeId()).get();

        assertThat(findLike1.getStatus()).isEqualTo(LikeStatus.LIKE);
        assertThat(findLike2.getStatus()).isEqualTo(LikeStatus.DELETED);
        assertThat(findLike3.getStatus()).isEqualTo(LikeStatus.DELETED);
        assertThat(findLike4.getStatus()).isEqualTo(LikeStatus.DELETED);
    }
}

