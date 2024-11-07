package com.fitin.community.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitin.auth.entity.Member;
import com.fitin.community.gamification.model.Badge;

/**
 * Badge 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    /**
     * 특정 회원이 아직 획득하지 않은 뱃지 중 현재 포인트와 레벨로 획득 가능한 뱃지를 조회합니다.
     * @param member 조회할 회원
     * @param points 현재 포인트
     * @param level 현재 레벨
     * @return 획득 가능한 뱃지 목록
     */
	@Query("SELECT b FROM Badge b WHERE b.id NOT IN (SELECT ub.badge.id FROM UserBadge ub WHERE ub.member = :member) AND b.requiredPoints <= :points AND b.requiredLevel <= :level")
    List<Badge> findBadgesNotAcquiredByUser(@Param("member") Member member, @Param("points") int points, @Param("level") int level);
}