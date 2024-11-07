package com.fitin.profile.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.profile.dto.FollowDto;
import com.fitin.profile.entity.Follow;
import com.fitin.profile.entity.Profile;
import com.fitin.profile.repository.FollowRepository;
import com.fitin.profile.repository.ProfileRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final ProfileRepository profileRepository;

    public FollowService(FollowRepository followRepository, ProfileRepository profileRepository) {
        this.followRepository = followRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public FollowDto follow(Long followerId, Long followedId) {
        Profile follower = profileRepository.findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("팔로워를 찾을 수 없습니다."));
        Profile followed = profileRepository.findById(followedId)
                .orElseThrow(() -> new EntityNotFoundException("팔로우할 사용자를 찾을 수 없습니다."));

        if (followRepository.existsByFollowerAndFollowed(follower, followed)) {
            throw new IllegalStateException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow(follower, followed);
        follow = followRepository.save(follow);
        return convertToDto(follow);
    }

    @Transactional
    public void unfollow(Long followerId, Long followedId) {
        Profile follower = profileRepository.findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("팔로워를 찾을 수 없습니다."));
        Profile followed = profileRepository.findById(followedId)
                .orElseThrow(() -> new EntityNotFoundException("언팔로우할 사용자를 찾을 수 없습니다."));

        Follow follow = followRepository.findByFollowerAndFollowed(follower, followed)
                .orElseThrow(() -> new EntityNotFoundException("팔로우 관계를 찾을 수 없습니다."));
        
        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public List<FollowDto> getFollowers(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("프로필을 찾을 수 없습니다."));
        return followRepository.findByFollowed(profile).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FollowDto> getFollowing(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("프로필을 찾을 수 없습니다."));
        return followRepository.findByFollower(profile).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FollowDto convertToDto(Follow follow) {
        FollowDto dto = new FollowDto();
        dto.setId(follow.getId());
        dto.setFollowerId(follow.getFollower().getId());
        dto.setFollowerNickname(follow.getFollower().getNickname());
        dto.setFollowedId(follow.getFollowed().getId());
        dto.setFollowedNickname(follow.getFollowed().getNickname());
        dto.setFollowDate(follow.getFollowDate());
        dto.setBlocked(follow.isBlocked());
        return dto;
    }
}