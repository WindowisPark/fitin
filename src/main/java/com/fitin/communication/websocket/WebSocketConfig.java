package com.fitin.communication.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // WebSocket 핸들러를 등록하고 엔드포인트를 지정합니다.
        // setAllowedOrigins("*")는 모든 오리진을 허용합니다. 실제 운영 환경에서는 필요한 오리진만 명시적으로 허용해야 합니다.
        registry.addHandler(new SmartMirrorHandler(), "/smart-mirror")
                .setAllowedOrigins("*");
    }
}