package com.fitin.exercise.selection.controller;

import com.fitin.exercise.selection.model.Exercise;
import com.fitin.exercise.selection.model.ExerciseProgram;
import com.fitin.exercise.selection.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Exercise>> getExercisesByCategory(@PathVariable("category") Exercise.Category category) {
        return ResponseEntity.ok(exerciseService.getExercisesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.createExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable("id") Long id, @RequestBody Exercise exercise) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, exercise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable("id") Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/programs")
    public ResponseEntity<List<ExerciseProgram>> getAllPrograms() {
        return ResponseEntity.ok(exerciseService.getAllPrograms());
    }

    @GetMapping("/programs/{id}")
    public ResponseEntity<ExerciseProgram> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getProgramById(id));
    }

    @PostMapping("/programs")
    public ResponseEntity<ExerciseProgram> createProgram(@RequestBody ExerciseProgram program) {
        ExerciseProgram createdProgram = exerciseService.createProgram(program);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProgram);
    }

    @PutMapping("/programs/{id}")
    public ResponseEntity<ExerciseProgram> updateProgram(@PathVariable Long id, @RequestBody ExerciseProgram program) {
        return ResponseEntity.ok(exerciseService.updateProgram(id, program));
    }

    @DeleteMapping("/programs/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        exerciseService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<Exercise>> recommendExercises(@RequestParam Long userId) {
        return ResponseEntity.ok(exerciseService.recommendExercises(userId));
    }
}