package com.fitin.community.routine.model;

import com.fitin.exercise.selection.model.Exercise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "routine_exercises")
@Getter
@Setter
public class RoutineExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
    
    @Column(nullable = false)
    private String exerciseName;

    @Column(nullable = false)
    private int sets;

    @Column(nullable = false)
    private int repsPerSet;

    @Column
    private int durationSeconds;

    @Column
    private String notes;
    
    // Getters and setters
    // 생성자
    public RoutineExercise(Routine routine, Exercise exercise, int sets, int repsPerSet) {
        this.routine = routine;
        this.exercise = exercise;
        this.sets = sets;
        this.repsPerSet = repsPerSet;
    }

    // 기본 생성자
    protected RoutineExercise() {}
}