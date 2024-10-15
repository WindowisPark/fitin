package com.fitin.exercise.record.model;

import com.fitin.auth.entity.Member;
import com.fitin.exercise.selection.model.Exercise;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercise_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private LocalDateTime date;

    private Integer duration; // in minutes

    private Integer sets;

    private Integer reps;

    private Double weight; // in kilograms

    @Column(nullable = true)
    private Integer score; // 스마트 거울에서 측정한 운동 점수

    @Column(nullable = true)
    private Double accuracyScore; // 자세 정확도 점수

    @Column(nullable = true)
    private Integer totalRepetitions; // 총 반복 횟수

    @Column(nullable = true)
    private Double caloriesBurned; // 소모 칼로리 추정치

    @Enumerated(EnumType.STRING)
    private Difficulty perceivedDifficulty;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 운동 당시의 사용자 신체 정보
    @Column(nullable = true)
    private Double memberWeight; // 운동 시 사용자의 체중

    @Column(nullable = true)
    private Double memberHeight; // 운동 시 사용자의 키

    public enum Difficulty {
        EASY, MODERATE, CHALLENGING, VERY_DIFFICULT
    }

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}