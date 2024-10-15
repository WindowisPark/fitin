package com.fitin.exercise.record.service;

import com.fitin.exercise.record.model.ExerciseRecord;
import java.time.LocalDateTime;
import java.util.List;

public interface ExerciseRecordService {
    ExerciseRecord saveExerciseRecord(ExerciseRecord record);
    ExerciseRecord getExerciseRecordById(Long id);
    List<ExerciseRecord> getExerciseRecordsByMemberId(Long memberId);
    List<ExerciseRecord> getExerciseRecordsByMemberIdAndDateRange(Long memberId, LocalDateTime start, LocalDateTime end);
    void deleteExerciseRecord(Long id);
    Object[] getStatsByMemberIdAndDateRange(Long memberId, LocalDateTime start, LocalDateTime end);
    Integer getPersonalBestScore(Long memberId, Long exerciseId);
}