package com.fitin.community.gamification.repository;

import com.fitin.auth.entity.Member;
import com.fitin.community.gamification.model.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserPoint 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {

    /**
     * 특정 회원의 포인트 정보를 조회합니다.
     * @param member 조회할 회원
     * @return 회원의 포인트 정보 (Optional)
     */
    Optional<UserPoint> findByMember(Member member);
}