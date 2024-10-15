package com.fitin.auth.repository;

import com.fitin.auth.entity.PasswordResetToken;
import com.fitin.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * 토큰으로 PasswordResetToken을 찾습니다.
     */
    Optional<PasswordResetToken> findByToken(String token);

    /**
     * 회원으로 PasswordResetToken을 찾습니다.
     */
    Optional<PasswordResetToken> findByMember(Member member);

    /**
     * 회원과 관련된 모든 PasswordResetToken을 삭제합니다.
     */
    void deleteByMember(Member member);

    /**
     * 만료된 토큰을 삭제합니다.
     */
    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

    /**
     * 특정 회원의 가장 최근 PasswordResetToken을 찾습니다.
     */
    @Query("SELECT t FROM PasswordResetToken t WHERE t.member = ?1 ORDER BY t.createdAt DESC")
    Optional<PasswordResetToken> findMostRecentTokenByMember(Member member);
}