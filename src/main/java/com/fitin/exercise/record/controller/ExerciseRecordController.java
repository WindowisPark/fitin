package com.fitin.exercise.record.controller;

import com.fitin.exercise.record.model.ExerciseRecord;
import com.fitin.exercise.record.service.ExerciseRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/exercise-records")
public class ExerciseRecordController {
    private final ExerciseRecordService exerciseRecordService;

    public ExerciseRecordController(ExerciseRecordService exerciseRecordService) {
        this.exerciseRecordService = exerciseRecordService;
    }

    @PostMapping
    public ResponseEntity<ExerciseRecord> createExerciseRecord(@RequestBody ExerciseRecord record) {
        return ResponseEntity.ok(exerciseRecordService.saveExerciseRecord(record));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseRecord> getExerciseRecord(@PathVariable("id") Long id) {
        return ResponseEntity.ok(exerciseRecordService.getExerciseRecordById(id));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ExerciseRecord>> getExerciseRecordsByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(exerciseRecordService.getExerciseRecordsByMemberId(memberId));
    }

    @GetMapping("/member/{memberId}/date-range")
    public ResponseEntity<List<ExerciseRecord>> getExerciseRecordsByMemberAndDateRange(
            @PathVariable("memberId") Long memberId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(exerciseRecordService.getExerciseRecordsByMemberIdAndDateRange(memberId, start, end));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseRecord(@PathVariable("id") Long id) {
        exerciseRecordService.deleteExerciseRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/{memberId}/stats")
    public ResponseEntity<Object[]> getMemberStats(
            @PathVariable("memberId") Long memberId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(exerciseRecordService.getStatsByMemberIdAndDateRange(memberId, start, end));
    }

    @GetMapping("/member/{memberId}/exercise/{exerciseId}/best-score")
    public ResponseEntity<Integer> getPersonalBestScore(
            @PathVariable("memberId") Long memberId,
            @PathVariable("exerciseId") Long exerciseId) {
        return ResponseEntity.ok(exerciseRecordService.getPersonalBestScore(memberId, exerciseId));
    }
}