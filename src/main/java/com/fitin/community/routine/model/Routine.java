package com.fitin.community.routine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fitin.auth.entity.Member;
import com.fitin.exercise.selection.model.Exercise;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "routines")
@Getter
@Setter
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineExercise> routineExercises = new ArrayList<>();

    @Column(nullable = false)
    private boolean isPublic = true;
    
    public void addRoutineExercise(RoutineExercise routineExercise) {
        routineExercises.add(routineExercise);
        routineExercise.setRoutine(this);
    }

    public void addExercise(Exercise exercise, int sets, int repsPerSet) {
        RoutineExercise routineExercise = new RoutineExercise(this, exercise, sets, repsPerSet);
        routineExercises.add(routineExercise);
    }

    public void removeExercise(Exercise exercise) {
        routineExercises.removeIf(routineExercise -> routineExercise.getExercise().equals(exercise));
    }

    public List<Exercise> getExercises() {
        return routineExercises.stream().map(RoutineExercise::getExercise).collect(Collectors.toList());
    }
}