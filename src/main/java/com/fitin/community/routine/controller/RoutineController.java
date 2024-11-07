package com.fitin.community.routine.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.entity.Member;
import com.fitin.community.routine.model.Routine;
import com.fitin.community.routine.model.RoutineExercise;
import com.fitin.community.routine.service.RoutineService;
import com.fitin.exercise.selection.model.Exercise;
import com.fitin.exercise.selection.service.ExerciseService;

@RestController
@RequestMapping("/api/community/routines")
public class RoutineController {

    private final RoutineService routineService;
    private final ExerciseService exerciseService;
    
    public RoutineController(RoutineService routineService, ExerciseService exerciseService) {
        this.routineService = routineService;
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<Routine> createRoutine(@RequestBody Routine routine, @AuthenticationPrincipal Member member) {
        Routine createdRoutine = routineService.createRoutine(routine, member);
        return ResponseEntity.ok(createdRoutine);
    }

    @PostMapping("/{routineId}/exercises")
    public ResponseEntity<RoutineExercise> addExerciseToRoutine(@PathVariable Long routineId, @RequestBody ExerciseRequest request) {
        Exercise exercise = exerciseService.getExerciseById(request.getExerciseId());
        RoutineExercise addedExercise = routineService.addExerciseToRoutine(routineId, exercise, request.getSets(), request.getRepsPerSet());
        return ResponseEntity.ok(addedExercise);
    }

    @GetMapping
    public ResponseEntity<List<Routine>> getUserRoutines(@AuthenticationPrincipal Member member) {
        List<Routine> routines = routineService.getUserRoutines(member);
        return ResponseEntity.ok(routines);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Routine>> getPublicRoutines() {
        List<Routine> publicRoutines = routineService.getPublicRoutines();
        return ResponseEntity.ok(publicRoutines);
    }

    @PutMapping("/{routineId}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable Long routineId, @RequestBody Routine routine, @AuthenticationPrincipal Member member) {
        routine.setId(routineId);
        routine.setCreator(member);
        Routine updatedRoutine = routineService.updateRoutine(routine);
        return ResponseEntity.ok(updatedRoutine);
    }

    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long routineId, @AuthenticationPrincipal Member member) {
        routineService.deleteRoutine(routineId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{routineId}/copy")
    public ResponseEntity<Routine> copyRoutine(@PathVariable Long routineId, @AuthenticationPrincipal Member member) {
        Routine copiedRoutine = routineService.copyRoutine(routineId, member);
        return ResponseEntity.ok(copiedRoutine);
    }
    class ExerciseRequest {
        private Long exerciseId;
        private int sets;
        private int repsPerSet;

        // Getters and setters
        public Long getExerciseId() { return exerciseId; }
        public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }
        public int getSets() { return sets; }
        public void setSets(int sets) { this.sets = sets; }
        public int getRepsPerSet() { return repsPerSet; }
        public void setRepsPerSet(int repsPerSet) { this.repsPerSet = repsPerSet; }
    }
}

