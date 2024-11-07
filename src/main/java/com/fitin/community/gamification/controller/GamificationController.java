package com.fitin.community.gamification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.entity.Member;
import com.fitin.community.gamification.model.Badge;
import com.fitin.community.gamification.model.UserPoint;
import com.fitin.community.gamification.service.GamificationService;

/**
 * 게이미피케이션 관련 API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/gamification")
public class GamificationController {

    private final GamificationService gamificationService;

    public GamificationController(GamificationService gamificationService) {
        this.gamificationService = gamificationService;
    }

    /**
     * 현재 인증된 사용자의 포인트와 레벨 정보를 조회합니다.
     * @param member 인증된 사용자
     * @return UserPoint 객체
     */
    @GetMapping("/points")
    public ResponseEntity<UserPoint> getUserPoints(@AuthenticationPrincipal Member member) {
        UserPoint userPoint = gamificationService.getUserPoints(member);
        return ResponseEntity.ok(userPoint);
    }

    /**
     * 현재 인증된 사용자의 뱃지를 확인하고 새로운 뱃지를 부여합니다.
     * @param member 인증된 사용자
     * @return 새로 획득한 뱃지 목록
     */
    @PostMapping("/check-badges")
    public ResponseEntity<List<Badge>> checkAndAwardBadges(@AuthenticationPrincipal Member member) {
        UserPoint userPoint = gamificationService.getUserPoints(member);
        List<Badge> newBadges = gamificationService.checkAndAwardBadges(member, userPoint);
        return ResponseEntity.ok(newBadges);
    }
}