package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class RepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;

    public void cleanup(){
        commentRepository.deleteAll();
        replyRepository.deleteAll();
    }

    @DisplayName("Comment-Reply Mapping-Connection Test")
    @Test
    void saveTest(){

        //Given
        replyRepository.save(
                Reply.builder()
                        .comment(Comment
                                .builder()
                                .commentId(1L)
                                .build())
                        .replyId(1L)
                        .content("sample")
                        .build()
        );

        commentRepository.save(
                Comment.builder()
                    .content("this is sample test comment")
                    .commentId(1L)
                    .member(new Member(3L))
                    .gallery(new Gallery(2L))
                    .artwork(new Artwork(1L))
                    .build()
        );

        //when
        List<Comment> commentList = commentRepository.findAll();
        List<Reply> replyList = replyRepository.findAll();
        Comment comment = commentRepository.findById(1L).orElse(null);


        //then
        Comment savedComment = commentList.get(0);
        // 임시방편
        savedComment.changeCommentStatus(CommentStatus.DELETED);
        Reply savedReply = replyList.get(0);
        assertThat(savedComment.getContent()).isEqualTo("this is sample test comment");
        assertThat(savedComment.getCommentId()).isEqualTo(1L);
        assertThat(savedComment.getMember().getMemberId()).isEqualTo(3L);
        assertThat(savedComment.getGallery().getGalleryId()).isEqualTo(2L);
        assertThat(savedComment.getArtworkId()).isEqualTo(1L);
        assertThat(savedComment.getCommentStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(comment).isEqualTo(savedReply.getComment()); //mapping clear

    }

    @Test
    void findPageOfCommentOnGalleryTest(){

    }

    @Test
    void findPageOfCommentOnArtworkTest(){

    }

    @Test
    void findListOfReplyTest(){

    }


}
