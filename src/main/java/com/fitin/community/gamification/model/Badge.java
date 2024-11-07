package com.fitin.community.gamification.model;

import jakarta.persistence.*;

/**
 * 사용자가 획득할 수 있는 뱃지를 정의하는 엔티티
 */
@Entity
@Table(name = "badges")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String acquisitionCriteria;
    
    private int requiredPoints;
    
    private int requiredLevel;

    // Getters and setters
}