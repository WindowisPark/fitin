package com.fitin.community.gamification.model;

import com.fitin.auth.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자의 포인트와 레벨을 관리하는 엔티티
 */

@Entity
@Table(name = "user_points")
@Getter
@Setter
@NoArgsConstructor
public class UserPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private int level;

    
    public UserPoint(Member member) {
        this.member = member;
        this.points = 0;
        this.level = 1;
    }
    /**
     * 포인트를 증가시키고 필요시 레벨을 업데이트.
     * @param amount 증가시킬 포인트 양
     */
    public void addPoints(int amount) {
        this.points += amount;
        updateLevel();
    }

    /**
     * 현재 포인트에 기반하여 레벨을 업데이트.
     */
    public void updateLevel() {
        // TODO: 간단한 레벨 업 로직. 실제 구현 시 더 복잡한 로직이 필요.
        this.level = this.points / 1000 + 1;
    }

    public boolean hasLeveledUp(int oldLevel) {
        return this.level > oldLevel;
    }
    // Getters and setters
}