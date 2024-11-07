package com.fitin.community.challenge.repository;

import com.fitin.community.challenge.model.ChallengeParticipation;
import com.fitin.auth.entity.Member;
import com.fitin.community.challenge.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeParticipationRepository extends JpaRepository<ChallengeParticipation, Long> {
    List<ChallengeParticipation> findByParticipant(Member participant);
    List<ChallengeParticipation> findByChallenge(Challenge challenge);
    boolean existsByChallengeAndParticipant(Challenge challenge, Member participant);
}