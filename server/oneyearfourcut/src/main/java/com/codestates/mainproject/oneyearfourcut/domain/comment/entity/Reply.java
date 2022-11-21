package com.codestates.mainproject.oneyearfourcut.domain.comment.entity;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reply extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    private String content; // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment; // 댓글 id

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 작성자 회원 id

    @Enumerated(EnumType.STRING)
    private CommentStatus replyStatus; //삭제 여부

}
