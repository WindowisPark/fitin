package com.fitin.profile.dto;

import java.time.LocalDateTime;

public class FollowDto {

    private Long id;
    private Long followerId;
    private String followerNickname;
    private Long followedId;
    private String followedNickname;
    private LocalDateTime followDate;
    private boolean isBlocked;

    // 기본 생성자
    public FollowDto() {}

    // 모든 필드를 포함한 생성자
    public FollowDto(Long id, Long followerId, String followerNickname, Long followedId, String followedNickname, LocalDateTime followDate, boolean isBlocked) {
        this.id = id;
        this.followerId = followerId;
        this.followerNickname = followerNickname;
        this.followedId = followedId;
        this.followedNickname = followedNickname;
        this.followDate = followDate;
        this.isBlocked = isBlocked;
    }

    // Getter와 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public String getFollowerNickname() {
        return followerNickname;
    }

    public void setFollowerNickname(String followerNickname) {
        this.followerNickname = followerNickname;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }

    public String getFollowedNickname() {
        return followedNickname;
    }

    public void setFollowedNickname(String followedNickname) {
        this.followedNickname = followedNickname;
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