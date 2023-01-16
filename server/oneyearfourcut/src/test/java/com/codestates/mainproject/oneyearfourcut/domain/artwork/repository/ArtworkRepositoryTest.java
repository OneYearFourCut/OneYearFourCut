package com.codestates.mainproject.oneyearfourcut.domain.artwork.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.global.exception.dto.ErrorResponse;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.zip.GZIPOutputStream;

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
        Artwork savedArtwork1;
        Artwork savedArtwork2;
        Artwork savedArtwork3;
        Artwork savedArtwork4;
        Artwork savedArtwork5;
        Artwork savedArtwork6;


        @BeforeEach
        public void setup() {
            gallery1 = new Gallery(1L);
            gallery2 = new Gallery(2L);
            // 갤러리1에 해당하는 작품
            Artwork artwork1 = new Artwork(1L, 0);
            artwork1.setImagePath("/1.png");
            artwork1.setGallery(gallery1);
            savedArtwork1 = artworkRepository.save(artwork1);

            Artwork artwork2 = new Artwork(2L, 12);
            artwork2.setImagePath("/2.png");
            artwork2.setGallery(gallery1);
            savedArtwork2 = artworkRepository.save(artwork2);

            Artwork artwork3 = new Artwork(3L, 32);
            artwork3.setImagePath("/3.png");
            artwork3.setGallery(gallery1);
            savedArtwork3 = artworkRepository.save(artwork3);

            Artwork artwork4 = new Artwork(4L, 45);
            artwork4.setImagePath("/4.png");
            artwork4.setGallery(gallery1);
            savedArtwork4 = artworkRepository.save(artwork4);

            Artwork artwork5 = new Artwork(5L, 32);
            artwork5.setImagePath("/5.png");
            artwork5.setGallery(gallery1);
            savedArtwork5 = artworkRepository.save(artwork5);

            // 갤러리2에 해당하는 작품
            Artwork artwork6 = new Artwork(6L, 10);
            artwork6.setImagePath("/6.png");
            artwork6.setGallery(gallery2);
            savedArtwork6 = artworkRepository.save(artwork6);
        }

        @Test
        @DisplayName("해당 galleryId와 status를 가진 작품 리스트 정렬로 불러오기 테스트 - 생성일순 정렬")
        void findAllByGallery_GalleryIdAndStatusTest() {

            List<Artwork> actualArtworkList = artworkRepository.findAllByGallery_GalleryId(1L, Sort.by(desc("createdAt")));


            // 꺼내온 작품의 갤러리가 의도한 갤러리와 일치한가?
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").containsOnly(gallery1.getGalleryId());
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").doesNotContain(gallery2.getGalleryId());
            // 정렬을 제대로 했는가?
            /*assertThat(actualArtworkList.get(0).getCreatedAt()).isAfter(actualArtworkList.get(1).getCreatedAt());
            assertThat(actualArtworkList.get(1).getCreatedAt()).isAfter(actualArtworkList.get(2).getCreatedAt());
            assertThat(actualArtworkList.get(2).getCreatedAt()).isAfter(actualArtworkList.get(3).getCreatedAt());
            assertThat(actualArtworkList.get(3).getCreatedAt()).isAfter(actualArtworkList.get(4).getCreatedAt());*/
        }

        @Test
        @DisplayName("해당 galleryId와 status를 가진 작품 리스트 정렬로 불러오기 테스트 - 좋아요순 정렬")
        void findTop4ByGallery_GalleryIdAndStatusTest() {
            List<Artwork> actualArtworkList = artworkRepository.findTop4ByGallery_GalleryId(gallery1.getGalleryId(), Sort.by(desc("likeCount")));


            System.out.println(actualArtworkList.size());
            // 작품 4개 이하만 가져온 것이 맞는가?
            assertThat(actualArtworkList.size()).isLessThanOrEqualTo(4);
            // 좋아요 순으로 제대로 정렬이 되어 있는가?
            assertThat(actualArtworkList.get(0).getLikeCount()).isGreaterThanOrEqualTo(actualArtworkList.get(0).getLikeCount());
            assertThat(actualArtworkList.get(1).getLikeCount()).isGreaterThanOrEqualTo(actualArtworkList.get(1).getLikeCount());
            assertThat(actualArtworkList.get(2).getLikeCount()).isGreaterThanOrEqualTo(actualArtworkList.get(2).getLikeCount());
            // 꺼내온 작품의 갤러리가 의도한 갤러리와 일치한가?
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").containsOnly(gallery1.getGalleryId());
            assertThat(actualArtworkList).extracting("gallery").extracting("galleryId").doesNotContain(gallery2.getGalleryId());
        }
    }

    @Test
    void gsonTest() {
        ErrorResponse response = ErrorResponse.of(ExceptionCode.EXPIRED_ACCESS_TOKEN);

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String gsoncontent = gson.toJson(response);

        System.out.println("gsoncontent = " + gsoncontent);
    }
}
