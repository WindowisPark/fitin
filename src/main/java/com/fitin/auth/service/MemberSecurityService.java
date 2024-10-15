package com.fitin.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.fitin.auth.entity.Member;
import com.fitin.auth.entity.MemberRole;
import com.fitin.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

/**
 * Spring Security에서 사용자의 정보를 가져오는 인터페이스의 구현체입니다.
 * 사용자의 정보를 조회하고 인증에 필요한 정보를 제공합니다.
 */
@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {
    
    /**
     * 회원 정보를 조회하는 레포지토리입니다.
     */
    private final MemberRepository memberRepository;

    /**
     * 주어진 사용자 이메일로 사용자의 상세 정보를 조회합니다.
     * 
     * @param email 조회할 사용자의 이메일
     * @return 조회된 사용자의 상세 정보
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> _member = this.memberRepository.findByEmail(email);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 조회할 수 없습니다.");
        }
        Member member = _member.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // MemberRole의 Enum 값을 기반으로 권한 설정
        if (member.getRole() == MemberRole.ADMIN) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.name()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.name()));
        }
        
        // Spring Security의 User 객체 반환
        return new User(member.getEmail(), member.getPassword(), authorities);
    }
}