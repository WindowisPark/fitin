package com.fitin.exercise.selection.repository;

import com.fitin.exercise.selection.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCategory(Exercise.Category category);
    List<Exercise> findByDifficulty(Exercise.Difficulty difficulty);
    List<Exercise> findByCategoryAndDifficulty(Exercise.Category category, Exercise.Difficulty difficulty);
}