package com.fitin.community.challenge.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.community.challenge.model.Challenge;
import com.fitin.community.challenge.model.ChallengeParticipation;
import com.fitin.community.challenge.repository.ChallengeParticipationRepository;
import com.fitin.community.challenge.repository.ChallengeRepository;
import com.fitin.community.common.exception.ResourceNotFoundException;
import com.fitin.community.gamification.service.GamificationService;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeParticipationRepository participationRepository;
    private final GamificationService gamificationService;

    public ChallengeService(ChallengeRepository challengeRepository,
                            ChallengeParticipationRepository participationRepository,
                            GamificationService gamificationService) {
        this.challengeRepository = challengeRepository;
        this.participationRepository = participationRepository;
        this.gamificationService = gamificationService;
    }

    @Transactional
    public Challenge createChallenge(Challenge challenge, Member creator) {
        challenge.setCreator(creator);
        Challenge savedChallenge = challengeRepository.save(challenge);
        gamificationService.addPoints(creator, 30); // 챌린지 생성 시 포인트 부여
        return savedChallenge;
    }

    @Transactional
    public ChallengeParticipation joinChallenge(Long challengeId, Member participant) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found"));
        
        if (participationRepository.existsByChallengeAndParticipant(challenge, participant)) {
            throw new IllegalStateException("User already joined this challenge");
        }

        ChallengeParticipation participation = new ChallengeParticipation();
        participation.setChallenge(challenge);
        participation.setParticipant(participant);
        participation.setJoinDate(LocalDateTime.now());
        participation.setProgress(0);
        participation.setCompleted(false);

        ChallengeParticipation savedParticipation = participationRepository.save(participation);
        
        GamificationService.RewardResult reward = gamificationService.rewardAction(participant, "JOIN_CHALLENGE");
        // Handle reward result

        return savedParticipation;
    }
    @Transactional(readOnly = true)
    public List<Challenge> getActiveChallenges() {
        return challengeRepository.findByEndDateAfter(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<ChallengeParticipation> getUserParticipations(Member participant) {
        return participationRepository.findByParticipant(participant);
    }

    @Transactional
    public ChallengeParticipation updateProgress(Long participationId, Integer progress, Member participant) {
        ChallengeParticipation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found"));

        if (!participation.getParticipant().equals(participant)) {
            throw new IllegalStateException("User not authorized to update this participation");
        }

        participation.setProgress(progress);
        if (progress >= 100) {
            participation.setCompleted(true);
            GamificationService.RewardResult reward = gamificationService.rewardAction(participant, "COMPLETE_CHALLENGE");
            // Handle reward result
        }

        return participationRepository.save(participation);
    }

    @Transactional
    public void deleteChallenge(Long challengeId, Member creator) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found"));

        if (!challenge.getCreator().equals(creator)) {
            throw new IllegalStateException("User not authorized to delete this challenge");
        }

        challengeRepository.delete(challenge);
    }
}