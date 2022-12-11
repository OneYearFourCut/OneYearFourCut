

package com.codestates.mainproject.oneyearfourcut.domain.comment.entity;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentArtworkResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentGalleryResDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 작성자 회원 id

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;  //작품이 아닌 전시관 전체 댓글일때

    // Artwork 연관관계 매핑 추가 (단방향)
    @ManyToOne(optional = true)
    @JoinColumn(name = "art_id")
    private Artwork artwork;

    @OneToMany(mappedBy = "comment")
    public List<Reply> replyList = new ArrayList<>(); // 대댓글, targetEntity

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    /* 에러 방지용으로 일단 관련 필드 및 생성자 삭제 안 함 - wk */
    @Column
    private Long artworkId;
    @Builder
    public Comment(Long commentId, String content, Member member, Gallery gallery, Long artworkId, CommentStatus commentStatus) {
        this.commentId = commentId;
        this.content = content;
        this.member = member;
        this.gallery = gallery;
        this.artworkId = artworkId;
        this.commentStatus = commentStatus;
    }
    /* 여기까지 리팩토링 완료 후 삭제 예정*/
    @Builder
    public Comment(Long commentId, String content, Member member, Gallery gallery, Artwork artwork) {
        this.commentId = commentId;
        this.content = content;
        this.member = member;
        this.gallery = gallery;
        this.artwork = artwork;
        this.commentStatus = CommentStatus.VALID;
    }
    public void changeContent(String content) {
        this.content = content;
    }

    public void changeCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    // 전시관 - 댓글 조회용 (전체)
    public CommentGalleryResDto toCommentGalleryResponseDto(String imagePath){
        return CommentGalleryResDto.builder()
                .commentId(this.getCommentId())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .content(this.getContent())
                .artworkId(this.getArtworkId())
                .memberId(this.getMember().getMemberId())
                .nickname(this.getMember().getNickname())
                .imagePath(imagePath)
                .build();
    }
    // 원강 - 수정
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
    /* 여기까지 수정 */

    // 작품 - 댓글 조회용
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

}
