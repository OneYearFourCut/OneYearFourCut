

package com.codestates.mainproject.oneyearfourcut.domain.comment.entity;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "comment")
@NoArgsConstructor
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 30, nullable = false)
    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 작성자 회원 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;  //작품이 아닌 전시관 전체 댓글일때

    // Artwork 연관관계 매핑 추가 (단방향)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @OneToMany(mappedBy = "comment")
    public List<Reply> replyList = new ArrayList<>(); // 대댓글, targetEntity

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    /* Builder 및 생성자 */
    @Builder
    public Comment(Long commentId, String content, Member member, Gallery gallery, Artwork artwork) {
        this.commentId = commentId;
        this.content = content;
        this.member = member;
        this.gallery = gallery;
        this.artwork = artwork;
        this.commentStatus = CommentStatus.VALID;
    }
    public Comment(Long commentId) {
        this.commentId = commentId;
        this.commentStatus = CommentStatus.VALID;
    }

    /* Setter Getter */
    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public Long getArtworkId() {
        if (this.artwork == null) {
            return null;
        }
        return artwork.getArtworkId();
    }
    public String getImagePath() {
        if (this.artwork == null) {
            return null;
        }
        return artwork.getImagePath();
    }

    /* toDto */
    // 댓글 전체 조회
    public CommentGalleryResDto toCommentGalleryResponseDto() {
        return CommentGalleryResDto.builder()
                .commentId(this.commentId)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .content(this.content)
                .artworkId(this.getArtworkId())
                .memberId(this.member.getMemberId())
                .nickname(this.member.getNickname())
                .imagePath(this.getImagePath())
                .build();
    }
    // 작품 댓글 조회
    public CommentArtworkResDto toCommentArtworkResponseDto(){
        return CommentArtworkResDto.builder()
                .commentId(this.getCommentId())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .content(this.getContent())
                .memberId(this.getMember().getMemberId())
                .nickname(this.getMember().getNickname())
                .build();
    }
    public AlarmEvent toAlarmEvent(Long receiverId) {
        if (this.getArtworkId() == null) {  //전시회 댓글인 경우
            return AlarmEvent.builder()
                    .receiverId(receiverId)
                    .senderId(this.getMember().getMemberId())
                    .alarmType(AlarmType.COMMENT_GALLERY)
                    .galleryId(this.getGallery().getGalleryId())
                    .artworkId(null)
                    .build();
        }
        return AlarmEvent.builder() //작품 댓글인 경우
                .receiverId(receiverId)
                .senderId(this.getMember().getMemberId())
                .alarmType(AlarmType.COMMENT_ARTWORK)
                .galleryId(this.getGallery().getGalleryId())
                .artworkId(this.getArtwork().getArtworkId())
                .build();
    }

}
