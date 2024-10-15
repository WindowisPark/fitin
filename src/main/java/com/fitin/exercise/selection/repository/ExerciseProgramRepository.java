package com.fitin.exercise.selection.repository;

import com.fitin.exercise.selection.model.ExerciseProgram;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseProgramRepository extends JpaRepository<ExerciseProgram, Long> {
    List<ExerciseProgram> findByNameContaining(String name);

	List<ExerciseProgram> findAll();
}