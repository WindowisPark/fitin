package com.fitin.auth.service;

import com.fitin.auth.dto.ResetPasswordRequestDto;
import com.fitin.auth.dto.ResetPasswordTokenRequestDto;
import com.fitin.auth.entity.Member;
import com.fitin.auth.entity.PasswordResetToken;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.auth.repository.PasswordResetTokenRepository;
import com.fitin.auth.exception.MemberNotFoundException;
import com.fitin.auth.exception.InvalidResetTokenException;
import com.fitin.auth.exception.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String createPasswordResetTokenForMember(ResetPasswordRequestDto resetPasswordRequestDto) {
        Member member = memberRepository.findByEmail(resetPasswordRequestDto.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("Member not found with email: " + resetPasswordRequestDto.getEmail()));

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setMember(member);
        myToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24시간 유효
        myToken.setCreatedAt(LocalDateTime.now());

        passwordResetTokenRepository.save(myToken);

        return token;
    }

    @Transactional
    public void resetPassword(ResetPasswordTokenRequestDto resetPasswordTokenRequestDto) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(resetPasswordTokenRequestDto.getToken())
                .orElseThrow(() -> new InvalidResetTokenException("Invalid password reset token"));

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Password reset token has expired");
        }

        Member member = passwordResetToken.getMember();
        member.setPassword(passwordEncoder.encode(resetPasswordTokenRequestDto.getNewPassword()));
        member.incrementPasswordResetCount();

        memberRepository.save(member);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    // 기타 필요한 메서드들...
}