package com.fitin.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fitin.auth.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    
    Optional<Member> findByPhoneNumber(String phoneNumber);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhoneNumber(String phoneNumber);
    
    Optional<Member> findByEmailAndMembername(String email, String membername);
    
    Optional<Member> findByPhoneNumberAndMembername(String phoneNumber, String membername);
    
    // 추가된 메서드
    Optional<Member> findByMembername(String membername);
}