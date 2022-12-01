package com.codestates.mainproject.oneyearfourcut.domain.artwork.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Order.desc;

@DataJpaTest
public class ArtworkRepositoryTest {

    @Autowired
    private ArtworkRepository artworkRepository;

    @DisplayName("쿼리 메서드 관련")
    @Nested
    class QueryMethodTest {

        Gallery gallery1;
        Gallery gallery2;
        Artwork artwork1;
        Artwork artwork2;
        Artwork artwork3;
        Artwork artwork4;
        Artwork artwork5;
        Artwork artwork6;


        @BeforeEach
        public void setup() {
            gallery1 = new Gallery(1L);
            gallery2 = new Gallery(2L);

            // 갤러리1에 해당하는 작품
            artwork1 = artworkRepository.save(new Artwork(1L, 0));
            artwork1.setGallery(gallery1);
            artwork2 = artworkRepository.save(new Artwork(2L, 12));
            artwork2.setGallery(gallery1);
            artwork3 = artworkRepository.save(new Artwork(3L, 32));
            artwork3.setGallery(gallery1);
            artwork4 = artworkRepository.save(new Artwork(4L, 45));
            artwork4.setGallery(gallery1);
            artwork5 = artworkRepository.save(new Artwork(5L, 32));
            artwork5.setGallery(gallery1);

            // 갤러리2에 해당하는 작품
            artwork6 = artworkRepository.save(new Artwork(6L, 10));
            artwork6.setGallery(gallery2);
        }
        @Test
        @DisplayName("해당 galleryId와 status를 가진 작품 리스트 정렬로 불러오기 테스트 - 생성일순 정렬")
        void findAllByGallery_GalleryIdAndStatusTest() {

            List<Artwork> actualArtworkList = artworkRepository.findAllByGallery_GalleryIdAndStatus(1L, ArtworkStatus.REGISTRATION,
                    Sort.by(desc("createdAt")));

            // 꺼내온 작품 중 예상하지 못한 status를 가진 작품은 없는가?
            assertThat(actualArtworkList).extracting("status").containsOnly(ArtworkStatus.REGISTRATION);
            assertThat(actualArtworkList).extracting("status").doesNotContain(ArtworkStatus.DELETED);
            // 꺼내온 작품의 갤러리가 의도한 갤러리와 일치한가?
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").containsOnly(gallery1.getGalleryId());
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").doesNotContain(gallery2.getGalleryId());
            // 정렬을 제대로 했는가?
            assertThat(actualArtworkList.get(0).getCreatedAt()).isAfter(actualArtworkList.get(1).getCreatedAt());
            assertThat(actualArtworkList.get(1).getCreatedAt()).isAfter(actualArtworkList.get(2).getCreatedAt());
            assertThat(actualArtworkList.get(2).getCreatedAt()).isAfter(actualArtworkList.get(3).getCreatedAt());
            assertThat(actualArtworkList.get(3).getCreatedAt()).isAfter(actualArtworkList.get(4).getCreatedAt());
        }

        @Test
        @DisplayName("해당 galleryId와 status를 가진 작품 리스트 정렬로 불러오기 테스트 - 좋아요순 정렬")
        void findTop4ByGallery_GalleryIdAndStatusTest() {
            List<Artwork> actualArtworkList = artworkRepository.findTop4ByGallery_GalleryIdAndStatus(gallery1.getGalleryId(), ArtworkStatus.REGISTRATION,
                    Sort.by(desc("likeCount")));

            // 작품 4개 이하만 가져온 것이 맞는가?
            assertThat(actualArtworkList.size()).isLessThanOrEqualTo(4);
            // 좋아요 순으로 제대로 정렬이 되어 있는가?
            assertThat(actualArtworkList.get(0).getLikeCount()).isGreaterThanOrEqualTo(actualArtworkList.get(1).getLikeCount());
            assertThat(actualArtworkList.get(1).getLikeCount()).isGreaterThanOrEqualTo(actualArtworkList.get(2).getLikeCount());
            assertThat(actualArtworkList.get(2).getLikeCount()).isGreaterThanOrEqualTo(actualArtworkList.get(3).getLikeCount());
            // 꺼내온 작품의 갤러리가 의도한 갤러리와 일치한가?
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").containsOnly(gallery1.getGalleryId());
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").doesNotContain(gallery2.getGalleryId());
            // 꺼내온 작품 중 예상하지 못한 status를 가진 작품은 없는가?
            assertThat(actualArtworkList).extracting("status").containsOnly(ArtworkStatus.REGISTRATION);
            assertThat(actualArtworkList).extracting("status").doesNotContain(ArtworkStatus.DELETED);
        }
    }

}
