package com.fitin.communication.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    /**
     * 모바일 앱에 새 비디오가 업로드되었음을 알립니다.
     * @param videoId 업로드된 비디오의 ID
     */
    public void notifyNewVideoUploaded(String videoId) {
        // 여기에서 실제 알림 로직을 구현합니다.
        // 예: Firebase Cloud Messaging을 사용하여 푸시 알림을 보내거나
        // 모바일 앱의 폴링 엔드포인트에 새 비디오 정보를 추가
        System.out.println("Notification: New video uploaded with ID " + videoId);
    }

    /**
     * 사용자에게 에러 메시지를 알립니다.
     * @param userId 사용자 ID
     * @param errorMessage 에러 메시지
     */
    public void notifyError(String userId, String errorMessage) {
        // 에러 알림 로직 구현
        System.out.println("Error notification for user " + userId + ": " + errorMessage);
    }
}