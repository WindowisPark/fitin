package com.fitin.exercise.selection.service;

import com.fitin.exercise.selection.model.Exercise;
import com.fitin.exercise.selection.model.ExerciseProgram;
import java.util.List;

public interface ExerciseService {
    List<Exercise> getAllExercises();
    Exercise getExerciseById(Long id);
    List<Exercise> getExercisesByCategory(Exercise.Category category);
    Exercise createExercise(Exercise exercise);
    Exercise updateExercise(Long id, Exercise exercise);
    void deleteExercise(Long id);
    
    List<ExerciseProgram> getAllPrograms();
    ExerciseProgram getProgramById(Long id);
    ExerciseProgram createProgram(ExerciseProgram program);
    ExerciseProgram updateProgram(Long id, ExerciseProgram program);
    void deleteProgram(Long id);
    
    List<Exercise> recommendExercises(Long userId);
}