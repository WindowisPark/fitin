package com.fitin.exercise.selection.service;

import com.fitin.exercise.selection.model.Exercise;
import com.fitin.exercise.selection.model.ExerciseProgram;
import com.fitin.exercise.selection.repository.ExerciseRepository;
import com.fitin.exercise.selection.repository.ExerciseProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseProgramRepository programRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseProgramRepository programRepository) {
        this.exerciseRepository = exerciseRepository;
        this.programRepository = programRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Exercise not found with id: " + id));
    }

    @Override
    public List<Exercise> getExercisesByCategory(Exercise.Category category) {
        return exerciseRepository.findByCategory(category);
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        if (exercise.getDifficulty() == null) {
            exercise.setDifficulty(Exercise.Difficulty.UNSPECIFIED);
        }
        return exerciseRepository.save(exercise);
    }


    @Override
    public Exercise updateExercise(Long id, Exercise exercise) {
    	Exercise existingExercise = getExerciseById(id);
    		existingExercise.setName(exercise.getName());
    		existingExercise.setDescription(exercise.getDescription());
    		existingExercise.setCategory(exercise.getCategory());
    		existingExercise.setDifficulty(exercise.getDifficulty() != null ? exercise.getDifficulty() : Exercise.Difficulty.UNSPECIFIED);
    			return exerciseRepository.save(existingExercise);
    }
    
    @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    public List<ExerciseProgram> getAllPrograms() {
        return programRepository.findAll();
    }

    @Override
    public ExerciseProgram getProgramById(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Program not found with id: " + id));
    }

    @Override
    public ExerciseProgram createProgram(ExerciseProgram program) {
        return programRepository.save(program);
    }

    @Override
    public ExerciseProgram updateProgram(Long id, ExerciseProgram program) {
        ExerciseProgram existingProgram = getProgramById(id);
        existingProgram.setName(program.getName());
        existingProgram.setDescription(program.getDescription());
        existingProgram.setExercises(program.getExercises());
        return programRepository.save(existingProgram);
    }

    @Override
    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }

    @Override
    public List<Exercise> recommendExercises(Long userId) {
        // 여기에 추천 로직을 구현합니다.
        // 현재는 간단히 모든 운동을 반환합니다.
        return getAllExercises();
    }
}