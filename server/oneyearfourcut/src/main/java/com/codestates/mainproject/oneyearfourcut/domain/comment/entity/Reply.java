package com.codestates.mainproject.oneyearfourcut.domain.comment.entity;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Reply extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(length = 30, nullable = false) // nullable = false 추가해도 될까요?
    private String content; // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment; // 댓글 id

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 작성자 회원 id

    @Builder
    public Reply(Long replyId, String content, Comment comment, Member member) {
        this.replyId = replyId;
        this.content = content;
        this.comment = comment;
        this.member = member;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public ReplyResDto toReplyResponseDto(){
        return ReplyResDto.builder()
                .replyId(this.getReplyId())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .content(this.getContent())
                .memberId(this.getMember().getMemberId())
                .nickname(this.getMember().getNickname())
                .build();
    }

    public AlarmEvent toAlarmEvent(Long receiverId) {
        Long artworkId = this.getComment().getArtworkId();    // 작품 댓글이 아닌 경우라면 null이 됨
        AlarmType alarmType = artworkId == null ? AlarmType.REPLY_GALLERY : AlarmType.REPLY_ARTWORK; //null 여부에 따라 타입 결정

        return AlarmEvent.builder()
                .receiverId(receiverId)
                .senderId(this.getMember().getMemberId())
                .alarmType(alarmType)
                .galleryId(this.getComment().getGallery().getGalleryId())
                .artworkId(artworkId)
                .build();
    }
}
