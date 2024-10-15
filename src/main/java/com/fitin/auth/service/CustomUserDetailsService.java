package com.fitin.auth.service;

import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fitin.auth.dto.FindIdRequestDto;
import com.fitin.auth.entity.Member;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.shopping.exception.MemberNotFoundException;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
    public String findMemberId(FindIdRequestDto findIdRequestDto) {
        Member member = memberRepository.findByEmailAndMembername(findIdRequestDto.getEmail(), findIdRequestDto.getMembername())
                .orElseThrow(() -> new MemberNotFoundException("Member not found with given email and name"));
        return member.getEmail(); // 이메일을 ID로 사용
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }
    
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber);
    }
    // JWT 토큰에서 사용자 ID를 사용하는 경우를 위한 메서드
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        logger.debug("Attempting to load user by ID: {}", id);

        try {
            Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
            
            logger.debug("User found: {}", member.getEmail());

            return new User(member.getEmail(),
                            member.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole().name())));
        } catch (UsernameNotFoundException ex) {
            logger.error("User not found with id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("An error occurred while loading user by id: {}", id, ex);
            throw new RuntimeException("An error occurred during user loading", ex);
        }
    }
}