package com.jongho.common.config;

import com.jongho.common.interceptor.WebSocketAuthInterceptor;
import com.jongho.common.interceptor.WebSocketPathVariablesInterceptor;
import com.jongho.openChat.handler.WebSocketOpenChatHandler;
import com.jongho.openChat.handler.WebSocketOpenChatRoomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketAuthInterceptor webSocketAuthInterceptor;
    private final WebSocketPathVariablesInterceptor webSocketPathVariablesInterceptor;
    private final WebSocketOpenChatHandler webSocketOpenChatHandler;
    private final WebSocketOpenChatRoomHandler webSocketOpenChatRoomHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketOpenChatRoomHandler, "/open-chat-rooms")
                .addHandler(webSocketOpenChatHandler, "/open-chat-rooms/*/open-chats")
                .addInterceptors(webSocketAuthInterceptor)
                .addInterceptors(webSocketPathVariablesInterceptor)
                .setAllowedOrigins("*");
    }
}
