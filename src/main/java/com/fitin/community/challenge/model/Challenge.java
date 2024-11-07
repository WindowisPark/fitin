package com.fitin.community.challenge.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fitin.auth.entity.Member;

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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "challenges")
@Getter
@Setter
@NoArgsConstructor
public class Challenge {

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

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private String goal;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeParticipation> participations = new ArrayList<>();

    // Getters and setters

    public void addParticipation(ChallengeParticipation participation) {
        participations.add(participation);
        participation.setChallenge(this);
    }

    public void removeParticipation(ChallengeParticipation participation) {
        participations.remove(participation);
        participation.setChallenge(null);
    }
}