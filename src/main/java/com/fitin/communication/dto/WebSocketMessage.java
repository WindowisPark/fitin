package com.fitin.communication.dto;

import java.time.LocalDateTime;

public class WebSocketMessage {
    private String type;
    private String data;
    private LocalDateTime timestamp;

    // Default constructor
    public WebSocketMessage() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with type and data
    public WebSocketMessage(String type, String data) {
        this();
        this.type = type;
        this.data = data;
    }

    // Getters and setters
    
    // type: 메시지의 유형. 예를 들어, "VIDEO_START", "VIDEO_CHUNK", "VIDEO_END"
    public String getType() {
        return type;
    } 

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}