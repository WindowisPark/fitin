package com.fitin.auth.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.dto.FindIdRequestDto;
import com.fitin.auth.dto.MemberCreateForm;
import com.fitin.auth.dto.ResetPasswordRequestDto;
import com.fitin.auth.dto.ResetPasswordTokenRequestDto;
import com.fitin.auth.entity.Member;
import com.fitin.auth.service.MemberService;
import com.fitin.auth.service.PasswordResetService;
import com.fitin.auth.util.JwtTokenProvider;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;
    private final PasswordResetService passwordResetService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    
    
    public MemberController(MemberService memberService, 
                            PasswordResetService passwordResetService,
                            AuthenticationManager authenticationManager, 
                            JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.passwordResetService = passwordResetService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "두 개의 비밀번호가 일치하지 않습니다.");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            Member member = memberService.create(
                memberCreateForm.getEmail(),
                memberCreateForm.getPassword1(),
                memberCreateForm.getName(),
                memberCreateForm.getHeight(),
                memberCreateForm.getWeight(),
                memberCreateForm.getPhoneNumber(),
                memberCreateForm.getRole()
            );
            return ResponseEntity.ok("회원가입 성공: " + member.getEmail());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return ResponseEntity.badRequest().body("이미 등록된 사용자입니다." + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }

    /**
     * 로그인 요청을 처리합니다.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            String token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new LoginResponse("로그인 성공", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
    
    
    @GetMapping("/login")
    public ResponseEntity<?> loginPage() {
        return ResponseEntity.ok("로그인 페이지입니다.");
    }
    
    /**
     * ID 찾기 요청을 처리합니다.
     */
    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody FindIdRequestDto findIdRequestDto) {
        try {
            String memberId = memberService.findMemberId(findIdRequestDto);
            return ResponseEntity.ok(new FindIdResponse("ID 찾기 성공", memberId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ID 찾기 실패: " + e.getMessage());
        }
    }
    
    /**
     * 비밀번호 재설정 요청을 처리합니다.
     */
    @PostMapping("/reset-password-request")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        try {
            String token = passwordResetService.createPasswordResetTokenForMember(resetPasswordRequestDto);
            // 여기서 이메일 전송 로직을 구현해야 합니다.
            return ResponseEntity.ok("비밀번호 재설정 링크가 이메일로 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("비밀번호 재설정 요청 실패: " + e.getMessage());
        }
    }
    
    /**
     * 새 비밀번호 설정 요청을 처리합니다.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordTokenRequestDto resetPasswordTokenRequestDto) {
        try {
            passwordResetService.resetPassword(resetPasswordTokenRequestDto);
            return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("비밀번호 재설정 실패: " + e.getMessage());
        }
    }
    /**
     * 로그아웃 요청을 처리합니다.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            jwtTokenProvider.blacklistToken(token);
            return ResponseEntity.ok("로그아웃 성공");
        }
        return ResponseEntity.badRequest().body("유효하지 않은 토큰");
    }
    
    /**
     * 로그인 요청 DTO 클래스입니다.
     */
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * 로그인 응답 DTO 클래스입니다.
     */
    public static class LoginResponse {
        private String message;
        private String token;

        public LoginResponse(String message, String token) {
            this.message = message;
            this.token = token;
        }

        // Getters and setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }

    /**
     * ID 찾기 응답 DTO 클래스입니다.
     */
    public static class FindIdResponse {
        private String message;
        private String memberId;

        public FindIdResponse(String message, String memberId) {
            this.message = message;
            this.memberId = memberId;
        }

        // Getters and setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getMemberId() { return memberId; }
        public void setMemberId(String memberId) { this.memberId = memberId; }
    }
}
