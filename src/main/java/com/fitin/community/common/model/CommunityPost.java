package com.fitin.community.common.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitin.auth.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 커뮤니티 게시글을 나타내는 엔티티 클래스
 */
@Entity
@Table(name = "community_posts")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시글 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    /**
     * 게시글 유형 (일기, 루틴, 챌린지, 일반)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 게시글 공개 여부
     */
    @Column(nullable = false)
    private boolean isPublic = true;

    // Getters and setters ...

    /**
     * 게시글 유형을 나타내는 열거형
     */
    public enum PostType {
        DIARY, ROUTINE, CHALLENGE, GENERAL
    }

    /**
     * 게시글 생성 시 호출되어 생성 시간과 수정 시간을 설정
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 게시글 수정 시 호출되어 수정 시간을 업데이트
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}