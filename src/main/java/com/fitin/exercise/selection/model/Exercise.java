package com.fitin.exercise.selection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "exercises")
    private List<ExerciseProgram> programs = new ArrayList<>();
    
    // 명시적으로 getPrograms 메서드 추가
    public List<ExerciseProgram> getPrograms() {
        return programs;
    }

    // 프로그램 추가 메서드
    public void addProgram(ExerciseProgram program) {
        this.programs.add(program);
    }

    // 프로그램 제거 메서드
    public void removeProgram(ExerciseProgram program) {
        this.programs.remove(program);
    }
    
    // Difficulty enum 
    public enum Difficulty {
        UNSPECIFIED, BEGINNER, INTERMEDIATE, ADVANCED
    }


    // Category enum
    public enum Category {
        UPPER_BODY, LOWER_BODY, CORE, CARDIO, FULL_BODY
    }
}