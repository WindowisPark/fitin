package com.fitin.exercise.record.repository;

import com.fitin.exercise.record.model.ExerciseRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
    // 사용자별 운동 기록 조회
    List<ExerciseRecord> findByMemberId(@Param("memberId") Long memberId);

    // 특정 기간 내 사용자의 운동 기록 조회
    List<ExerciseRecord> findByMemberIdAndDateBetween(
        @Param("memberId") Long memberId, 
        @Param("start") LocalDateTime start, 
        @Param("end") LocalDateTime end
    );

    // 특정 운동에 대한 사용자의 모든 기록 조회
    List<ExerciseRecord> findByMemberIdAndExerciseId(
        @Param("memberId") Long memberId, 
        @Param("exerciseId") Long exerciseId
    );

    List<ExerciseRecord> findTopByMemberIdOrderByDateDesc(
        @Param("memberId") Long memberId, 
        @Param("pageable") Pageable pageable
    );

    // 기간별 통계 쿼리 (예: 총 운동 시간, 평균 스코어)
    @Query("SELECT SUM(er.duration) as totalDuration, AVG(er.score) as avgScore, " +
           "AVG(er.memberWeight) as avgWeight, AVG(er.memberHeight) as avgHeight " +
           "FROM ExerciseRecord er " +
           "WHERE er.member.id = :memberId AND er.date BETWEEN :start AND :end")
    Object[] getStatsByMemberIdAndDateRange(
        @Param("memberId") Long memberId, 
        @Param("start") LocalDateTime start, 
        @Param("end") LocalDateTime end
    );

    // 특정 운동에 대한 개인 최고 기록
    @Query("SELECT MAX(er.score) FROM ExerciseRecord er " +
           "WHERE er.member.id = :memberId AND er.exercise.id = :exerciseId")
    Integer getPersonalBestScore(
        @Param("memberId") Long memberId, 
        @Param("exerciseId") Long exerciseId
    );
}