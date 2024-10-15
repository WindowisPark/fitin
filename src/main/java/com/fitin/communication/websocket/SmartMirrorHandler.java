package com.fitin.communication.websocket;

import com.fitin.communication.dto.WebSocketMessage;
import com.fitin.communication.service.WebSocketService;
import com.fitin.communication.util.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SmartMirrorHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private WebSocketMessageProcessor messageProcessor;

    /**
     * WebSocket 연결이 설정되었을 때 호출됩니다.
     * @param session 새로 설정된 WebSocket 세션
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketService.addSession(session);
        System.out.println("New WebSocket connection established: " + session.getId());
        
        // 연결 성공 메시지를 클라이언트에게 전송
        WebSocketMessage connectedMessage = new WebSocketMessage("CONNECTED", "Successfully connected to server");
        sendMessage(session, connectedMessage);
    }

    /**
     * 클라이언트로부터 메시지를 수신했을 때 호출됩니다.
     * @param session 메시지를 보낸 WebSocket 세션
     * @param message 수신된 텍스트 메시지
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from " + session.getId() + ": " + payload);

        try {
            WebSocketMessage webSocketMessage = MessageConverter.jsonToWebSocketMessage(payload);
            messageProcessor.processMessage(session, webSocketMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            WebSocketMessage errorMessage = new WebSocketMessage("ERROR", "Failed to process message: " + e.getMessage());
            sendMessage(session, errorMessage);
        }
    }

    /**
     * WebSocket 연결이 종료되었을 때 호출됩니다.
     * @param session 종료된 WebSocket 세션
     * @param status 연결 종료 상태
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketService.removeSession(session);
        System.out.println("WebSocket connection closed for " + session.getId() + ", status: " + status);
    }

    /**
     * WebSocket 통신 중 에러가 발생했을 때 호출됩니다.
     * @param session 에러가 발생한 WebSocket 세션
     * @param exception 발생한 예외
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Transport error on session " + session.getId() + ": " + exception.getMessage());
        WebSocketMessage errorMessage = new WebSocketMessage("ERROR", "Transport error occurred: " + exception.getMessage());
        sendMessage(session, errorMessage);
    }

    /**
     * WebSocket 세션에 메시지를 전송합니다.
     * @param session 메시지를 전송할 WebSocket 세션
     * @param message 전송할 WebSocketMessage
     */
    private void sendMessage(WebSocketSession session, WebSocketMessage message) {
        try {
            String jsonMessage = MessageConverter.toJson(message);
            session.sendMessage(new TextMessage(jsonMessage));
        } catch (Exception e) {
            System.err.println("Failed to send message to session " + session.getId() + ": " + e.getMessage());
        }
    }
}