package com.jongho.common.util.redis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongho.common.exception.MyJsonProcessingException;
import com.jongho.common.util.websocket.BaseWebSocketMessage;
import com.jongho.openChat.application.dto.OpenChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BaseRedisTemplate {
    private final StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public <T> void setData(String key, T value) {
        stringRedisTemplate.opsForValue().set(key, toJson(value));
    }

    public <T> T getData(String key, Class<T> valueType) {
        String jsonValue = stringRedisTemplate.opsForValue().get(key);
        if(jsonValue == null){
            return null;
        }
        return toObject(jsonValue, valueType);
    }

    public <T> List<T> getAllListData(String key, Class<T> valueType) {
        List<String> jsonValue = stringRedisTemplate.opsForList().range(key, 0, -1);
        if(jsonValue == null){
            return null;
        }
        return mappingToElement(jsonValue, valueType);
    }

    public <T> List<T> getReverseRangeAllListData(String key, Class<T> valueType) {
        List<String> jsonValue = stringRedisTemplate.opsForList().range(key, 0, -1);
        if(jsonValue == null){
            return null;
        }
        List<T> result = mappingToElement(jsonValue, valueType);
        Collections.reverse(result);

        return result;
    }

    public <T> List<T> getReverseRangeListData(String key, Class<T> valueType, int offset, int limit) {
        List<String> jsonValue = stringRedisTemplate.opsForList().range(key, offset, limit);
        if(jsonValue == null){
            return null;
        }
        List<T> result = mappingToElement(jsonValue, valueType);
        Collections.reverse(result);

        return result;
    }

    public <T> List<T> popListData(String key, Class<T> valueType, int limit) {
        List<String> jsonValue = stringRedisTemplate.opsForList().leftPop(key, limit);
        if(jsonValue == null){
            return null;
        }

        return mappingToElement(jsonValue, valueType);
    }


    public <T> void setAllListData(String key, List<T> value) {
        stringRedisTemplate.opsForList().rightPushAll(key, value.stream().map(this::toJson).collect(Collectors.toList()));
    }

    public <T> void setListData(String key, T value) {
        stringRedisTemplate.opsForList().rightPush(key, toJson(value));
    }

    public <T> List<T> getListData(String key, Class<T> valueType, int offset, int limit) {
        List<String> jsonValue = stringRedisTemplate.opsForList().range(key, offset, limit);
        if(jsonValue == null){
            return null;
        }
        return mappingToElement(jsonValue, valueType);
    }

    public void setHashData(String key, Map<String, String> value) {
        stringRedisTemplate.opsForHash().putAll(key, value);
    }

    public <T> void setHashDataColumn(String key, String column, T value) {
        stringRedisTemplate.opsForHash().put(key, column, value);
    }
    public void incrementHashDataColumn(String key, String column, int value) {
        stringRedisTemplate.opsForHash().increment(key, column, value);
    }

    public Cursor<String> scan(ScanOptions options) {
        return stringRedisTemplate.scan(options);
    }

    public <T> T getHashData(String key, Class<T> valueType) {
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);
        if(map.isEmpty()){
            return null;
        }
        return mapToObject(map, valueType);
    }

    public void publish(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

    public void subscribe(String channel, MessageListener messageListener) {
        Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()).getConnection().subscribe(messageListener, channel.getBytes());
    }

    public void subscribe(List<String> channel, MessageListener messageListener) {
        for(String c : channel) {
            Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()).getConnection().subscribe(messageListener, c.getBytes());
        }
    }

    public <T> T toObject(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        }catch (Exception e) {
            throw new MyJsonProcessingException(e.getMessage()!=null? e.getMessage():"json processing error");
        }
    }

    public <T> T mapToObject(Map<Object, Object> map, Class<T> valueType) {
        try {
            return objectMapper.convertValue(map, valueType);
        }catch (Exception e) {
            throw new MyJsonProcessingException(e.getMessage()!=null? e.getMessage():"json processing error");
        }
    }

    public String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        }catch (Exception e) {
            throw new MyJsonProcessingException(e.getMessage()!=null? e.getMessage():"json processing error");
        }
    }

    public BaseWebSocketMessage getWebSocketMessage(TextMessage textMessage){
        try {
            return objectMapper.readValue(textMessage.getPayload(), BaseWebSocketMessage.class);
        }catch (Exception e) {
            throw new MyJsonProcessingException(e.getMessage()!=null? e.getMessage():"json processing error");
        }

    }

    private <T> List<T> mappingToElement(Collection<String> jsonList, Class<T> valueType){
        return jsonList.stream().map(json -> toObject(json, valueType)).collect(Collectors.toList());
    }
}