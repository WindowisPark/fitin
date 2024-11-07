package com.fitin.community.routine.repository;

import com.fitin.community.routine.model.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
    List<RoutineExercise> findByRoutineId(Long routineId);
}