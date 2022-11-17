package com.codestates.mainproject.oneyearfourcut.artwork.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ArtworkRepositoryTest {

    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("artwork save - jpa 편의 메소드 테스트")
    public void saveArtworkGalleryRepository() {
        // given
        Member member1 = new Member();
        member1.setEmail("test1@gmail.com");
        member1.setNickname("주최자");
        Member admin = memberRepository.save(member1);

        Member member2 = new Member();
        member2.setEmail("test2@gmail.com");
        member2.setNickname("작성자");
        Member writer = memberRepository.save(member2);

        Gallery gallery = new Gallery();
        gallery.setMember(admin);
        gallery.setTitle("길동의 전시관");
        gallery.setContent("환영합니다.");
        Gallery savedGallery = galleryRepository.save(gallery);

        Artwork artwork = new Artwork();
        artwork.setTitle("작품제목");
        artwork.setContent("설명");
        artwork.setImgPath("/img.png");
        artwork.setMember(writer);
        artwork.setGallery(savedGallery);
        Artwork savedArtwork = artworkRepository.save(artwork);

        // when
        Long exceptedAdminId = admin.getMemberId();
        Long actualAdminId = savedArtwork.getGallery().getMember().getMemberId();

        String exceptedAdminName = admin.getNickname();
        String actualAdminName = savedArtwork.getGallery().getMember().getNickname();

        Long exceptedWriterId = writer.getMemberId();
        Long actualWriterId = savedArtwork.getMember().getMemberId();

        String exceptedWriterName = writer.getNickname();
        String actualWriterName = savedArtwork.getMember().getNickname();

        Long exceptedGalleryId = savedGallery.getGalleryId();
        Long actualGalleryId = savedArtwork.getGallery().getGalleryId();

        // than
        assertEquals(exceptedAdminId, actualAdminId);
        assertEquals(exceptedAdminName, actualAdminName);
        assertEquals(exceptedWriterId, actualWriterId);
        assertEquals(exceptedWriterName, actualWriterName);
        assertEquals(exceptedGalleryId, actualGalleryId);
    }
}
