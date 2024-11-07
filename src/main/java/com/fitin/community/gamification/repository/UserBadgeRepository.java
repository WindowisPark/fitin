package com.fitin.community.gamification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitin.community.gamification.model.UserBadge;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    // 추가 쿼리 메서드가 필요하다면 여기에 정의
}
