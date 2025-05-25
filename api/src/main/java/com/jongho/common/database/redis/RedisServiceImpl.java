package com.jongho.common.database.redis;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.serializer.DataSerializer;
import com.jongho.common.util.websocket.BaseWebSocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{
    private final BaseRedisTemplate baseRedisTemplate;
    public void publish(String channel, String message) {
        baseRedisTemplate.publish(channel, message);
    }
    public void subscribe(String channel, WebSocketSession session) {
        baseRedisTemplate.subscribe(channel, getMessageHandler(session));
    }
    public void subscribe(List<String> channel, WebSocketSession session) {
        baseRedisTemplate.subscribe(channel, getMessageHandler(session));
    }

    private RedisMessageHandler getMessageHandler(WebSocketSession session) {
        return new RedisMessageHandler(session);
    }
    public BaseWebSocketMessage convertStringMessageToBaseWebSocketMessage(TextMessage message){
        return DataSerializer.deserialize(message.getPayload(), BaseWebSocketMessage.class);
    }
    public <T> T dataToObject(String data, Class<T> valueType){
        return baseRedisTemplate.toObject(data, valueType);
    }
    public String objectToData(Object object){
        return baseRedisTemplate.toJson(object);
    }
}

@Log4j2
@RequiredArgsConstructor
class RedisMessageHandler implements MessageListener {
    private final WebSocketSession session;

    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        try {
            if(session.isOpen()){
                synchronized (session) {
                    session.sendMessage(new TextMessage(new String(message.getBody())));
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
