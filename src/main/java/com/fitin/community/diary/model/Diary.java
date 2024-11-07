package com.fitin.community.diary.model;

import com.fitin.community.common.model.CommunityPost;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 운동 일기를 나타내는 엔티티 클래스
 */
@Entity
public class Diary extends CommunityPost {

    @Column(nullable = false)
    private String exerciseType;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Integer caloriesBurned;

    @Column(nullable = false)
    private LocalDateTime exerciseDate;

    @Column(nullable = false)
    private boolean isPublic = true;

    // Getters and setters

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * 운동 일기의 상세 정보를 문자열로 반환합니다.
     */
    @Override
    public String toString() {
        return String.format("Diary{exerciseType='%s', durationMinutes=%d, caloriesBurned=%d, exerciseDate=%s, isPublic=%b}",
                exerciseType, durationMinutes, caloriesBurned, exerciseDate, isPublic);
    }
}