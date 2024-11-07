package com.fitin.profile.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notification_settings")
@Getter
@Setter
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private boolean emailNotifications = true;

    @Column(nullable = false)
    private boolean pushNotifications = true;

    @Column(nullable = false)
    private boolean newFollowerNotifications = true;

    @Column(nullable = false)
    private boolean newMessageNotifications = true;

    // 기타 필요한 알림 설정 필드들...
}