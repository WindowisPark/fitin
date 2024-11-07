package com.fitin.community.gamification.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.community.gamification.model.Badge;
import com.fitin.community.gamification.model.UserBadge;
import com.fitin.community.gamification.model.UserPoint;
import com.fitin.community.gamification.repository.AchievementRepository;
import com.fitin.community.gamification.repository.BadgeRepository;
import com.fitin.community.gamification.repository.UserBadgeRepository;
import com.fitin.community.gamification.repository.UserPointRepository;

/**
 * 게이미피케이션 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
public class GamificationService {

    private final UserPointRepository userPointRepository;
    private final BadgeRepository badgeRepository;
    private final AchievementRepository achievementRepository;
    
    @Autowired
    private UserBadgeRepository userBadgeRepository;

    private static final Map<String, Integer> ACTION_POINTS = Map.of(
            "CREATE_DIARY", 10,
            "CREATE_ROUTINE", 20,
            "JOIN_CHALLENGE", 15,
            "COMPLETE_CHALLENGE", 50,
            "DAILY_LOGIN", 5
        );
    
    
    public GamificationService(UserPointRepository userPointRepository,
                               BadgeRepository badgeRepository,
                               AchievementRepository achievementRepository) {
        this.userPointRepository = userPointRepository;
        this.badgeRepository = badgeRepository;
        this.achievementRepository = achievementRepository;
    }
    
    /**
     * 사용자에게 포인트를 추가합니다.
     * @param member 포인트를 받을 사용자
     * @param points 추가할 포인트 양
     * @return 업데이트된 UserPoint 객체
     */
    @Transactional
    public UserPoint addPoints(Member member, int points) {
        UserPoint userPoint = userPointRepository.findByMember(member)
                .orElseGet(() -> new UserPoint(member));
        userPoint.addPoints(points);
        return userPointRepository.save(userPoint);
    }

    /**
     * 사용자의 현재 포인트와 레벨을 조회합니다.
     * @param member 조회할 사용자
     * @return UserPoint 객체
     */
    @Transactional(readOnly = true)
    public UserPoint getUserPoints(Member member) {
        return userPointRepository.findByMember(member)
                .orElseGet(() -> new UserPoint(member));
    }

    /**
     * 사용자가 새로운 뱃지를 획득했는지 확인하고, 획득했다면 부여합니다.
     * @param member 확인할 사용자
     * @return 새로 획득한 뱃지 목록
     */
    
    public List<Badge> checkAndAwardBadges(Member member, UserPoint userPoint) {
        List<Badge> newBadges = badgeRepository.findBadgesNotAcquiredByUser(member, userPoint.getPoints(), userPoint.getLevel());
        for (Badge badge : newBadges) {
            UserBadge userBadge = new UserBadge();
            userBadge.setMember(member);
            userBadge.setBadge(badge);
            userBadge.setAcquiredDate(LocalDateTime.now());
            userBadgeRepository.save(userBadge);
        }
        return newBadges;
    }
    
    @Transactional
    public RewardResult rewardAction(Member member, String action) {
        UserPoint userPoint = userPointRepository.findByMember(member)
                .orElseGet(() -> new UserPoint(member));

        int pointsGained = ACTION_POINTS.getOrDefault(action, 0);
        userPoint.addPoints(pointsGained);

        boolean leveledUp = checkAndUpdateLevel(userPoint);
        List<Badge> badgesEarned = checkAndAwardBadges(member, action, userPoint);

        userPointRepository.save(userPoint);

        return new RewardResult(pointsGained, leveledUp, badgesEarned);
    }
    
    private boolean checkAndUpdateLevel(UserPoint userPoint) {
        int oldLevel = userPoint.getLevel();
        userPoint.updateLevel();
        return userPoint.hasLeveledUp(oldLevel);
    }
    
    private List<Badge> checkAndAwardBadges(Member member, String action, UserPoint userPoint) {
        List<Badge> newBadges = badgeRepository.findBadgesNotAcquiredByUser(member, userPoint.getPoints(), userPoint.getLevel());
        for (Badge badge : newBadges) {
            // Logic to award badge to user
            // This might involve creating a new UserBadge entity
        }
        return newBadges;
    }
    
    public static class RewardResult {
        public final int pointsGained;
        public final boolean leveledUp;
        public final List<Badge> badgesEarned;

        public RewardResult(int pointsGained, boolean leveledUp, List<Badge> badgesEarned) {
            this.pointsGained = pointsGained;
            this.leveledUp = leveledUp;
            this.badgesEarned = badgesEarned;
        }
    }
}