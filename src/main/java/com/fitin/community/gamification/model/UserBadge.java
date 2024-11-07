package com.fitin.community.gamification.model;

import java.time.LocalDateTime;

import com.fitin.auth.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_badges")
@Getter
@Setter
@NoArgsConstructor
public class UserBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Column(name = "acquired_date")
    private LocalDateTime acquiredDate;

    // Getters and setters
}