package com.fitin.communication.websocket;

import com.fitin.communication.dto.WebSocketMessage;
import com.fitin.communication.dto.VideoMetadata;
import com.fitin.communication.service.VideoTransferService;
import com.fitin.communication.service.NotificationService;
import com.fitin.communication.util.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketMessageProcessor {

    @Autowired
    private VideoTransferService videoTransferService;

    @Autowired
    private NotificationService notificationService;

    public void processMessage(WebSocketSession session, WebSocketMessage message) {
        try {
            switch (message.getType()) {
                case "VIDEO_START":
                    handleVideoStart(session, message);
                    break;
                case "VIDEO_CHUNK":
                    handleVideoChunk(session, message);
                    break;
                case "VIDEO_END":
                    handleVideoEnd(session, message);
                    break;
                default:
                    handleUnknownMessageType(session, message);
            }
        } catch (Exception e) {
            handleError(session, e);
        }
    }

    private void handleVideoStart(WebSocketSession session, WebSocketMessage message) {
        VideoMetadata metadata = MessageConverter.jsonToVideoMetadata(message.getData());
        videoTransferService.startVideoTransfer(session, metadata);
        System.out.println("Video transfer started: " + metadata.getFileName());
    }

    private void handleVideoChunk(WebSocketSession session, WebSocketMessage message) {
        byte[] chunkData = MessageConverter.fromJson(message.getData(), byte[].class);
        videoTransferService.processVideoChunk(session, chunkData);
        System.out.println("Received video chunk of size: " + chunkData.length);
    }

    private void handleVideoEnd(WebSocketSession session, WebSocketMessage message) {
        String videoId = videoTransferService.completeVideoTransfer(session);
        System.out.println("Video transfer completed. Video ID: " + videoId);
        notificationService.notifyNewVideoUploaded(videoId);
    }

    private void handleUnknownMessageType(WebSocketSession session, WebSocketMessage message) {
        System.out.println("Received unknown message type: " + message.getType());
    }

    private void handleError(WebSocketSession session, Exception e) {
        System.err.println("Error processing message: " + e.getMessage());
        notificationService.notifyError(session.getId(), "Error processing video: " + e.getMessage());
    }
}