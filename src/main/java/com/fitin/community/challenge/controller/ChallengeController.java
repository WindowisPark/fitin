package com.fitin.community.challenge.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.entity.Member;
import com.fitin.community.challenge.model.Challenge;
import com.fitin.community.challenge.model.ChallengeParticipation;
import com.fitin.community.challenge.service.ChallengeService;

@RestController
@RequestMapping("/api/community/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    public ResponseEntity<Challenge> createChallenge(@RequestBody Challenge challenge, @AuthenticationPrincipal Member member) {
        Challenge createdChallenge = challengeService.createChallenge(challenge, member);
        return ResponseEntity.ok(createdChallenge);
    }

    @PostMapping("/{challengeId}/join")
    public ResponseEntity<ChallengeParticipation> joinChallenge(@PathVariable Long challengeId, @AuthenticationPrincipal Member member) {
        ChallengeParticipation participation = challengeService.joinChallenge(challengeId, member);
        return ResponseEntity.ok(participation);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Challenge>> getActiveChallenges() {
        List<Challenge> activeChallenges = challengeService.getActiveChallenges();
        return ResponseEntity.ok(activeChallenges);
    }

    @GetMapping("/participations")
    public ResponseEntity<List<ChallengeParticipation>> getUserParticipations(@AuthenticationPrincipal Member member) {
        List<ChallengeParticipation> participations = challengeService.getUserParticipations(member);
        return ResponseEntity.ok(participations);
    }

    @PatchMapping("/participations/{participationId}/progress")
    public ResponseEntity<ChallengeParticipation> updateProgress(@PathVariable Long participationId, 
                                                                 @RequestParam Integer progress, 
                                                                 @AuthenticationPrincipal Member member) {
        ChallengeParticipation updatedParticipation = challengeService.updateProgress(participationId, progress, member);
        return ResponseEntity.ok(updatedParticipation);
    }

    @DeleteMapping("/{challengeId}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long challengeId, @AuthenticationPrincipal Member member) {
        challengeService.deleteChallenge(challengeId, member);
        return ResponseEntity.noContent().build();
    }
}