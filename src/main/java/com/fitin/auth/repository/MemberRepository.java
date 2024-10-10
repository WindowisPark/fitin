package com.fitin.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.fitin.auth.entity.Member;

public interface MemberRepository extends JpaRepository<Member , Long> {
	Optional<Member> findByMembername(String membername);
}
