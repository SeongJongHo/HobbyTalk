package com.jongho.common.database.redis;

import com.jongho.common.util.websocket.BaseWebSocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface RedisService {
    void publish(String channel, String message);
    void subscribe(String channel, WebSocketSession session);
    void subscribe(List<String> channel, WebSocketSession session);
    BaseWebSocketMessage convertStringMessageToBaseWebSocketMessage(TextMessage message);
    <T> T dataToObject(String data, Class<T> valueType);
    String objectToData(Object object);
}
