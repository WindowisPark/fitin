package com.fitin.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitin.auth.dto.AdminSignupRequest;
import com.fitin.auth.dto.AdminSignupResponse;
import com.fitin.auth.dto.FindIdRequestDto;
import com.fitin.auth.entity.Member;
import com.fitin.auth.entity.MemberRole;
import com.fitin.auth.exception.MemberNotFoundException;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.auth.util.JwtTokenProvider;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberService {
	
	private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    
    public MemberService(MemberRepository memberRepository, 
                         PasswordEncoder passwordEncoder,
                         JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
	
    @Transactional
    public Member create(String email, String password, String name, Double height, Double weight, String phoneNumber, MemberRole role) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setMembername(name);
        member.setHeight(height);
        member.setWeight(weight);
        member.setPhoneNumber(phoneNumber);
        member.setRole(role);
        return memberRepository.save(member);
    }
	
	// 관리자 회원가입 메서드 수정
    public AdminSignupResponse createAdmin(AdminSignupRequest request) {
        if (memberRepository.findByMembername(request.getMembername()).isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }

        Member member = new Member();
        member.setMembername(request.getMembername());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setCreatedAt(LocalDateTime.now());
        member.setRole(MemberRole.ADMIN);

        Member savedMember = this.memberRepository.save(member);
        System.out.println("관리자가 저장되었습니다: " + savedMember);

        return new AdminSignupResponse("관리자 계정이 성공적으로 생성되었습니다.", savedMember.getMembername());
    }

    public String login(String membername, String password) {
        System.out.println("Attempting login for user: " + membername);
        // 1. 사용자 검증
        Member member = memberRepository.findByMembername(membername)
            .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));
        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        // 3. 인증 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
        // 4. JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);
    }
    
    public String findMemberId(FindIdRequestDto findIdRequestDto) {
        Member member = memberRepository.findByEmailAndMembername(findIdRequestDto.getEmail(), findIdRequestDto.getMembername())
                .orElseThrow(() -> new MemberNotFoundException("Member not found with given email and name"));
        return member.getEmail(); // 이메일을 ID로 사용
    }

}
