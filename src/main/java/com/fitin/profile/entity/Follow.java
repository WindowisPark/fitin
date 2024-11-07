package com.fitin.profile.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 팔로우를 하는 사용자 (팔로워)
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Profile follower;

    // 팔로우를 받는 사용자 (팔로잉)
    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private Profile followed;

    // 팔로우 시작 시간
    @Column(name = "follow_date", nullable = false)
    private LocalDateTime followDate;

    // 차단 여부
    @Column(name = "is_blocked")
    private boolean isBlocked = false;

    // 기본 생성자
    public Follow() {
    }

    // 팔로우 관계 생성 시 사용할 생성자
    public Follow(Profile follower, Profile followed) {
        this.follower = follower;
        this.followed = followed;
        this.followDate = LocalDateTime.now();
    }

    // Getter와 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getFollower() {
        return follower;
    }

    public void setFollower(Profile follower) {
        this.follower = follower;
    }

    public Profile getFollowed() {
        return followed;
    }

    public void setFollowed(Profile followed) {
        this.followed = followed;
    }

    public LocalDateTime getFollowDate() {
        return followDate;
    }

    public void setFollowDate(LocalDateTime followDate) {
        this.followDate = followDate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}