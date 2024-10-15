package com.fitin.exercise.record.service;

import com.fitin.exercise.record.model.ExerciseRecord;
import com.fitin.exercise.record.repository.ExerciseRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ExerciseRecordServiceImpl implements ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    public ExerciseRecordServiceImpl(ExerciseRecordRepository exerciseRecordRepository) {
        this.exerciseRecordRepository = exerciseRecordRepository;
    }

    @Override
    public ExerciseRecord saveExerciseRecord(ExerciseRecord record) {
        return exerciseRecordRepository.save(record);
    }

    @Override
    public ExerciseRecord getExerciseRecordById(Long id) {
        return exerciseRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise record not found"));
    }

    @Override
    public List<ExerciseRecord> getExerciseRecordsByMemberId(Long memberId) {
        return exerciseRecordRepository.findByMemberId(memberId);
    }

    @Override
    public List<ExerciseRecord> getExerciseRecordsByMemberIdAndDateRange(Long memberId, LocalDateTime start, LocalDateTime end) {
        return exerciseRecordRepository.findByMemberIdAndDateBetween(memberId, start, end);
    }

    @Override
    public void deleteExerciseRecord(Long id) {
        exerciseRecordRepository.deleteById(id);
    }

    @Override
    public Object[] getStatsByMemberIdAndDateRange(Long memberId, LocalDateTime start, LocalDateTime end) {
        return exerciseRecordRepository.getStatsByMemberIdAndDateRange(memberId, start, end);
    }

    @Override
    public Integer getPersonalBestScore(Long memberId, Long exerciseId) {
        return exerciseRecordRepository.getPersonalBestScore(memberId, exerciseId);
    }
}