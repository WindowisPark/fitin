package com.fitin.communication.service;

import com.fitin.communication.dto.WebSocketMessage;
import com.fitin.communication.util.MessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WebSocketService {

    // 동시성 문제를 방지하기 위해 CopyOnWriteArrayList를 사용합니다.
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    /**
     * 새로운 WebSocket 세션을 추가합니다.
     * @param session 추가할 WebSocket 세션
     */
    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * WebSocket 세션을 제거합니다.
     * @param session 제거할 WebSocket 세션
     */
    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    /**
     * 모든 연결된 세션에 메시지를 브로드캐스팅합니다.
     * @param message 브로드캐스팅할 WebSocketMessage
     */
    public void broadcastMessage(WebSocketMessage message) {
        String jsonMessage = MessageConverter.toJson(message);
        TextMessage textMessage = new TextMessage(jsonMessage);
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(textMessage);
                } catch (IOException e) {
                    System.err.println("Failed to send message to session " + session.getId() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * 특정 세션에 메시지를 전송합니다.
     * @param session 메시지를 전송할 WebSocket 세션
     * @param message 전송할 WebSocketMessage
     */
    public void sendMessageToSession(WebSocketSession session, WebSocketMessage message) {
        if (session.isOpen()) {
            try {
                String jsonMessage = MessageConverter.toJson(message);
                session.sendMessage(new TextMessage(jsonMessage));
            } catch (IOException e) {
                System.err.println("Failed to send message to session " + session.getId() + ": " + e.getMessage());
            }
        }
    }

    /**
     * 현재 연결된 세션의 수를 반환합니다.
     * @return 연결된 세션의 수
     */
    public int getSessionCount() {
        return sessions.size();
    }
}