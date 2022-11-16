

package com.codestates.mainproject.oneyearfourcut.domain.comment.entity;


import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtWork;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;


    private String content; // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 작성자 회원 id

    @ManyToOne
    @JoinColumn(name = "galley_id")
    private Gallery gallery;  //작품이 아닌 전시관 전체 댓글일때

    private Long artWorkId;  //작품에 달린 댓글일때.

    @OneToMany(mappedBy = "comment")
    private List<Reply> replyList = new ArrayList<>(); // 대댓글, targetEntity


}
