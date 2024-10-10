package com.fitin.auth.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitin.auth.entity.Member;
import com.fitin.auth.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional


public class MemberService {
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Member create(String membername, String email, String password) {
		Member member = new Member();
		member.setMembername(membername);
		member.setEmail(email);
		member.setPassword(passwordEncoder.encode(password));
		member.setPassword(passwordEncoder.encode(password));
		 member.setCreatedAt(LocalDateTime.now()); // created_at 필드에 현재 시간 설정
		this.memberRepository.save(member);
		Member savedMember = this.memberRepository.save(member);
		System.out.println("회원이 저장되었습니다: " + savedMember);
		return savedMember;
	}
}
