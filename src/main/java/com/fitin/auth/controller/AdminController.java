package com.fitin.auth.controller;

import com.fitin.auth.dto.AdminSignupRequest;
import com.fitin.auth.dto.AdminSignupResponse;
import com.fitin.auth.service.MemberService;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MemberService memberService;

    @PostMapping("/signup")
    // TODO: 토큰 알고리즘 구현 @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminSignupResponse> signupAdmin(@RequestBody AdminSignupRequest request) {
        AdminSignupResponse response = memberService.createAdmin(request);
        return ResponseEntity.ok(response);
    }
    
}