package com.fitin.community.gamification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitin.community.gamification.model.Achievement;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    // 필요한 추가 쿼리 메서드들...
}