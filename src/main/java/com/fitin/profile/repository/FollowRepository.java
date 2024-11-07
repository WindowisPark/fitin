package com.fitin.profile.repository;

import com.fitin.profile.entity.Follow;
import com.fitin.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    // 특정 사용자가 팔로우하는 목록 찾기
    List<Follow> findByFollower(Profile follower);

    // 특정 사용자를 팔로우하는 목록 찾기
    List<Follow> findByFollowed(Profile followed);

    // 특정 팔로우 관계 찾기
    Optional<Follow> findByFollowerAndFollowed(Profile follower, Profile followed);

    // 팔로우 관계 존재 여부 확인
    boolean existsByFollowerAndFollowed(Profile follower, Profile followed);

    // 추가적인 쿼리 메서드는 필요에 따라 여기에 정의할 수 있습니다.
}