package com.codestates.mainproject.oneyearfourcut.domain.follow.repository;

import com.codestates.mainproject.oneyearfourcut.domain.follow.entity.Follow;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FollowRepositoryTest {
    @Autowired
    private FollowRepository followRepository;

    @BeforeEach
    void setUp(){
        //given
        Follow follow1 = Follow.builder() //1번유저가 2번갤러리 follow
                .member(new Member(1L))
                .followMemberId(2L)
                .gallery(new Gallery(2L))
                .isFollowTogetherCheck(true)
                .build();
        Follow follow2 = Follow.builder() //2번유저가 1번갤러리 follow
                .member(new Member(2L))
                .followMemberId(1L)
                .gallery(new Gallery(1L))
                .isFollowTogetherCheck(true)
                .build();
        Follow follow3 = Follow.builder() //3번유저가 2번갤러리 follow
                .member(new Member(3L))
                .followMemberId(2L)
                .gallery(new Gallery(2L))
                .isFollowTogetherCheck(false)
                .build();
        List<Follow> followList = List.of(follow1, follow2, follow3);

        followRepository.saveAll(followList);
    }
    @AfterEach
    @DisplayName("")
    void testClear(){
        followRepository.deleteAll();
    }

    @Test
    @DisplayName("")
    void testFind(){
        //when
        Follow foundFollow = followRepository.findById(2L).orElseThrow();
        //then
        assertThat(foundFollow.getFollowId()).isEqualTo(2L);
    }
    @Test
    @DisplayName("")
    void testDeleteFollow(){
        //when
        followRepository.deleteById(4L);
        //then
        assertThat(followRepository.count()).isEqualTo(2);
    }


    @Test
    @DisplayName("")
    void testDeleteFollowByMemberAndGallery(){
        //given
        Follow follow4 = Follow.builder() //3번유저가 1번갤러리 follow
                .member(new Member(3L))
                .followMemberId(1L)
                .gallery(new Gallery(1L))
                .isFollowTogetherCheck(false)
                .build();
        followRepository.save(follow4);

        //when
        Optional<Follow> foundFollow = followRepository.findByMemberAndGallery(follow4.getMember(), follow4.getGallery());
        /*followRepository.delete(foundFollow);*/

        //then
        assertThat(followRepository.count()).isEqualTo(4);
    }

    @Test
    void jpqlTest() {
        followRepository.findByFollowMemberIdAndMember(1L, new Member(1L));
    }


}
