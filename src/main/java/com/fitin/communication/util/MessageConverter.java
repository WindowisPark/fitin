package com.fitin.communication.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitin.communication.dto.WebSocketMessage;
import com.fitin.communication.dto.VideoMetadata;

public class MessageConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    public static WebSocketMessage jsonToWebSocketMessage(String json) {
        return fromJson(json, WebSocketMessage.class);
    }

    public static VideoMetadata jsonToVideoMetadata(String json) {
        return fromJson(json, VideoMetadata.class);
    }
}