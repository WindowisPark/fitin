package com.fitin.community.routine.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.community.common.exception.ResourceNotFoundException;
import com.fitin.community.gamification.service.GamificationService;
import com.fitin.community.routine.model.Routine;
import com.fitin.community.routine.model.RoutineExercise;
import com.fitin.community.routine.repository.RoutineExerciseRepository;
import com.fitin.community.routine.repository.RoutineRepository;
import com.fitin.exercise.selection.model.Exercise;

@Service
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final RoutineExerciseRepository routineExerciseRepository;
    private final GamificationService gamificationService;

 
    public RoutineService(RoutineRepository routineRepository, 
                          RoutineExerciseRepository routineExerciseRepository,
                          GamificationService gamificationService) {
        this.routineRepository = routineRepository;
        this.routineExerciseRepository = routineExerciseRepository;
        this.gamificationService = gamificationService;
    }

    @Transactional
    public Routine createRoutine(Routine routine, Member creator) {
        routine.setCreator(creator);
        Routine savedRoutine = routineRepository.save(routine);
        GamificationService.RewardResult reward = gamificationService.rewardAction(creator, "CREATE_ROUTINE");
        return savedRoutine;
    }

    @Transactional
    public RoutineExercise addExerciseToRoutine(Long routineId, Exercise exercise, int sets, int repsPerSet) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine not found"));
        RoutineExercise routineExercise = new RoutineExercise(routine, exercise, sets, repsPerSet);
        routine.addRoutineExercise(routineExercise);
        return routineExerciseRepository.save(routineExercise);
    }

    @Transactional(readOnly = true)
    public List<Routine> getUserRoutines(Member member) {
        return routineRepository.findByCreator(member);
    }

    @Transactional(readOnly = true)
    public List<Routine> getPublicRoutines() {
        return routineRepository.findByIsPublicTrue();
    }

    @Transactional
    public Routine updateRoutine(Routine routine) {
        return routineRepository.save(routine);
    }

    @Transactional
    public void deleteRoutine(Long routineId) {
        routineRepository.deleteById(routineId);
    }

    @Transactional
    public Routine copyRoutine(Long routineId, Member newCreator) {
        Routine originalRoutine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Routine not found"));

        Routine copiedRoutine = new Routine();
        copiedRoutine.setName(originalRoutine.getName() + " (Copy)");
        copiedRoutine.setDescription(originalRoutine.getDescription());
        copiedRoutine.setCreator(newCreator);
        copiedRoutine.setPublic(false); // 복사된 루틴은 기본적으로 비공개로 설정

        Routine savedCopiedRoutine = routineRepository.save(copiedRoutine);

        for (RoutineExercise routineExercise : originalRoutine.getRoutineExercises()) {
            Exercise exercise = routineExercise.getExercise();
            int sets = routineExercise.getSets();
            int repsPerSet = routineExercise.getRepsPerSet();
            addExerciseToRoutine(savedCopiedRoutine.getId(), exercise, sets, repsPerSet);
        }

        return savedCopiedRoutine;
    }
}