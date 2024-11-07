package com.fitin.community.routine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitin.auth.entity.Member;
import com.fitin.community.routine.model.Routine;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findByCreator(Member creator);
    List<Routine> findByIsPublicTrue();
}
