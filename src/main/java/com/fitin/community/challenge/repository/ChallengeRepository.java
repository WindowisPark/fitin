package com.fitin.community.challenge.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitin.auth.entity.Member;
import com.fitin.community.challenge.model.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByCreator(Member creator);
    List<Challenge> findByEndDateAfter(LocalDateTime date);
}

