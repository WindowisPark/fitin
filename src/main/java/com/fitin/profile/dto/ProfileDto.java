package com.fitin.profile.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileDto {
	 private Long id;

	    @NotBlank(message = "닉네임은 필수입니다.")
	    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
	    private String nickname;

	    // 팔로워 수
	    private int followerCount;

	    // 팔로잉 수
	    private int followingCount;

	    // 기본 생성자
	    public ProfileDto() {}

	    // 모든 필드를 포함한 생성자
	    public ProfileDto(Long id, String nickname, int followerCount, int followingCount) {
	        this.id = id;
	        this.nickname = nickname;
	        this.followerCount = followerCount;
	        this.followingCount = followingCount;
	    }

	    // Getter와 Setter 메서드
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getNickname() {
	        return nickname;
	    }

	    public void setNickname(String nickname) {
	        this.nickname = nickname;
	    }

	    public int getFollowerCount() {
	        return followerCount;
	    }

	    public void setFollowerCount(int followerCount) {
	        this.followerCount = followerCount;
	    }

	    public int getFollowingCount() {
	        return followingCount;
	    }

	    public void setFollowingCount(int followingCount) {
	        this.followingCount = followingCount;
	    }
}
