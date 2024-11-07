package com.fitin.profile.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class NotificationSettingsDto {

    @NotNull(message = "이메일 알림 설정은 필수입니다.")
    private Boolean emailNotifications;

    @NotNull(message = "푸시 알림 설정은 필수입니다.")
    private Boolean pushNotifications;

    @NotNull(message = "새 팔로워 알림 설정은 필수입니다.")
    private Boolean newFollowerNotifications;

    @NotNull(message = "새 메시지 알림 설정은 필수입니다.")
    private Boolean newMessageNotifications;

    // 기본 생성자
    public NotificationSettingsDto() {}

    // 모든 필드를 포함하는 생성자
    public NotificationSettingsDto(Boolean emailNotifications, Boolean pushNotifications, 
                                   Boolean newFollowerNotifications, Boolean newMessageNotifications) {
        this.emailNotifications = emailNotifications;
        this.pushNotifications = pushNotifications;
        this.newFollowerNotifications = newFollowerNotifications;
        this.newMessageNotifications = newMessageNotifications;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "NotificationSettingsDto{" +
               "emailNotifications=" + emailNotifications +
               ", pushNotifications=" + pushNotifications +
               ", newFollowerNotifications=" + newFollowerNotifications +
               ", newMessageNotifications=" + newMessageNotifications +
               '}';
    }
}