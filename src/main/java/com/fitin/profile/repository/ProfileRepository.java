package com.fitin.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitin.auth.entity.Member;
import com.fitin.profile.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
	   // 닉네임으로 프로필 찾기
    Optional<Profile> findByNickname(String nickname);

    // 닉네임 존재 여부 확인
    boolean existsByNickname(String nickname);

    // Member와 ID로 프로필 찾기
    Optional<Profile> findByMemberAndId(Member member, Long id);

    // 추가적인 쿼리 메서드는 필요에 따라 여기에 정의.
}