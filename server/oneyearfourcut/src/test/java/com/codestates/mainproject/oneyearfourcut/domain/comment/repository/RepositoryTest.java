package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
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

    @Autowired
    ArtworkRepository artworkRepository;

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

    @Test
    @DisplayName("해당 artworkId를 가진 Comment 삭제 상태 변경 테스트")
    void deleteCommentByArtworkIdTest() {
        Artwork artwork1 = Artwork.builder().title("작품1").content("설명1").build();
        artwork1.setImagePath("/test1.jpg");
        artworkRepository.save(artwork1);
        Artwork artwork2 = Artwork.builder().title("작품2").content("설명2").build();
        artwork2.setImagePath("/test2.jpg");
        artworkRepository.save(artwork2);

        Comment comment1 = commentRepository.save(Comment.builder().content("댓글1").artwork(artwork1).build());
        Comment comment2 = commentRepository.save(Comment.builder().content("댓글2").artwork(artwork1).build());
        Comment comment3 = commentRepository.save(Comment.builder().content("댓글3").artwork(artwork1).build());
        Comment comment4 = commentRepository.save(Comment.builder().content("댓글4").artwork(artwork1).build());
        Comment comment5 = commentRepository.save(Comment.builder().content("댓글5").artwork(artwork2).build());
        Comment comment6 = commentRepository.save(Comment.builder().content("댓글6").artwork(artwork2).build());

        commentRepository.deleteByArtworkId(comment1.getArtworkId());

        Comment findComment1 = commentRepository.findById(comment1.getCommentId()).get();
        Comment findComment2 = commentRepository.findById(comment2.getCommentId()).get();
        Comment findComment3 = commentRepository.findById(comment3.getCommentId()).get();
        Comment findComment4 = commentRepository.findById(comment4.getCommentId()).get();
        Comment findComment5 = commentRepository.findById(comment5.getCommentId()).get();
        Comment findComment6 = commentRepository.findById(comment6.getCommentId()).get();

        assertThat(findComment1.getCommentStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findComment2.getCommentStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findComment3.getCommentStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findComment4.getCommentStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findComment5.getCommentStatus()).isNotEqualTo(CommentStatus.DELETED);
        assertThat(findComment6.getCommentStatus()).isNotEqualTo(CommentStatus.DELETED);
    }

    @Test
    @DisplayName("해당 CommentId를 가진 Reply 삭제 상태 변경 테스트")
    void deleteReplyByCommentIdTest() {
        Comment comment1 = commentRepository.save(Comment.builder().content("댓글1").build());
        Comment comment2 = commentRepository.save(Comment.builder().content("댓글2").build());

        Reply reply1 = replyRepository.save(Reply.builder().content("테스트1").comment(comment1).build());
        Reply reply2 = replyRepository.save(Reply.builder().content("테스트2").comment(comment1).build());
        Reply reply3 = replyRepository.save(Reply.builder().content("테스트3").comment(comment1).build());
        Reply reply4 = replyRepository.save(Reply.builder().content("테스트4").comment(comment1).build());
        Reply reply5 = replyRepository.save(Reply.builder().content("테스트4").comment(comment2).build());
        Reply reply6 = replyRepository.save(Reply.builder().content("테스트4").comment(comment2).build());

        replyRepository.deleteByCommentId(comment1.getCommentId());

        Reply findReply1 = replyRepository.findById(reply1.getReplyId()).get();
        Reply findReply2 = replyRepository.findById(reply2.getReplyId()).get();
        Reply findReply3 = replyRepository.findById(reply3.getReplyId()).get();
        Reply findReply4 = replyRepository.findById(reply4.getReplyId()).get();
        Reply findReply5 = replyRepository.findById(reply5.getReplyId()).get();
        Reply findReply6 = replyRepository.findById(reply6.getReplyId()).get();

        assertThat(findReply1.getReplyStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findReply2.getReplyStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findReply3.getReplyStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findReply4.getReplyStatus()).isEqualTo(CommentStatus.DELETED);
        assertThat(findReply5.getReplyStatus()).isNotEqualTo(CommentStatus.DELETED);
        assertThat(findReply6.getReplyStatus()).isNotEqualTo(CommentStatus.DELETED);
    }
}
