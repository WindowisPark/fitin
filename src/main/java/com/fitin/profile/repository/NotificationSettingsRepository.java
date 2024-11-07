package com.fitin.profile.repository;

import com.fitin.profile.entity.NotificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {
    NotificationSettings findByProfileId(Long profileId);
}