package com.jongho.common.util.redis;

import com.jongho.common.util.serializer.DataSerializer;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BaseRedisTemplate  {
    private final StringRedisTemplate stringRedisTemplate;

    public <T> void set(String key, T value) {
        stringRedisTemplate.opsForValue().set(key, toJson(value));
    }

    public <T> void set(String key, T value, Duration timeout) {
        stringRedisTemplate.opsForValue().set(key, toJson(value), timeout);
    }

    public <T> T get(String key, Class<T> valueType) {
        String jsonValue = stringRedisTemplate.opsForValue().get(key);

        return jsonValue == null? null: toObject(jsonValue, valueType);
    }

    public <T> List<T> lPopList(String key, Class<T> valueType, int limit) {
        List<String> jsonValue = stringRedisTemplate.opsForList().leftPop(key, limit);

        return jsonValue == null? null: mappingToElement(jsonValue, valueType);
    }

    public <T> List<T> rPopList(String key, Class<T> valueType, int limit) {
        List<String> jsonValue = stringRedisTemplate.opsForList().rightPop(key, limit);

        return jsonValue == null ? null : mappingToElement(jsonValue, valueType);
    }

    public void trimList(String key, int start, int end) {
        stringRedisTemplate.opsForList().trim(key, start, end);
    }

    public void rename(String oldKey, String newKey) {
        stringRedisTemplate.rename(oldKey, newKey);
    }

    public <T> void rPushList(String key, List<T> value) {
        stringRedisTemplate.opsForList()
            .rightPushAll(key, value.stream().map(this::toJson).collect(Collectors.toList()));
    }

    public <T> void rPushList(String key, List<T> value, Duration timeout) {
        if (isEmpty(key)) {
            stringRedisTemplate.opsForList()
                .rightPushAll(key, value.stream().map(this::toJson).collect(Collectors.toList()));
            expire(key, timeout);
            return;
        }
        stringRedisTemplate.opsForList()
            .rightPushAll(key, value.stream().map(this::toJson).collect(Collectors.toList()));
    }

    public <T> void rPushList(String key, T value) {
        stringRedisTemplate.opsForList().rightPush(key, toJson(value));
    }

    public <T> void rPushList(String key, T value, Duration timeout) {
        Long result = stringRedisTemplate.opsForList().rightPush(key, toJson(value));
        expire(result, key, timeout);
    }

    public <T> void lPushList(String key, List<T> value) {
        stringRedisTemplate.opsForList()
            .leftPushAll(key, value.stream().map(this::toJson).collect(Collectors.toList()));
    }

    public <T> void lPushList(String key, List<T> value, Duration timeout) {
        if (isEmpty(key)) {
            stringRedisTemplate.opsForList()
                .leftPushAll(key, value.stream().map(this::toJson).collect(Collectors.toList()));
            expire(key, timeout);
            return;
        }
        stringRedisTemplate.opsForList()
            .leftPushAll(key, value.stream().map(this::toJson).collect(Collectors.toList()));
    }

    public <T> void lPushList(String key, T value) {
        stringRedisTemplate.opsForList().leftPush(key, toJson(value));
    }

    public <T> void lPushList(String key, T value, Duration timeout) {
        Long result = stringRedisTemplate.opsForList().leftPush(key, toJson(value));
        expire(result, key, timeout);
    }

    public <T> void pushSet(String key, T value) {
        if (Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, value))) {
            return;
        }
        stringRedisTemplate.opsForSet().add(key, toJson(value));
    }

    public <T> List<T> getList(String key, Class<T> valueType, int offset, int limit) {
        List<String> jsonValue = stringRedisTemplate.opsForList().range(key, offset, limit);
        if(jsonValue == null){
            return null;
        }
        return mappingToElement(jsonValue, valueType);
    }

    public Long getListSize(String key) {
        Long size = stringRedisTemplate.opsForList().size(key);

        return size == null ? 0L : size;
    }

    public <T> List<T> getReverseList(String key, Class<T> valueType, int offset, int limit) {
        List<String> jsonValue = stringRedisTemplate.opsForList().range(key, offset, limit);
        if(jsonValue == null){
            return null;
        }
        List<T> result = mappingToElement(jsonValue, valueType);
        Collections.reverse(result);

        return result;
    }

    public void putHash(String key, Map<String, String> value) {
        stringRedisTemplate.opsForHash().putAll(key, value);
    }

    public <T> void putHash(String key, String column, T value) {
        stringRedisTemplate.opsForHash().put(key, column, value);
    }
    public void incrementHash(String key, String column, int value) {
        stringRedisTemplate.opsForHash().increment(key, column, value);
    }

    public Long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    public Cursor<String> scan(ScanOptions options) {
        return stringRedisTemplate.scan(options);
    }

    public <T> T getHash(String key, Class<T> valueType) {
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);

        return map.isEmpty()? null: mapToObject(map, valueType);
    }

    public void publish(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

    public void streamPublish(String channel, String message) {
        stringRedisTemplate.opsForStream().add(channel, Collections.singletonMap("data", message));
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
        return DataSerializer.deserialize(json, valueType);
    }

    public <T> T mapToObject(Map<Object, Object> map, Class<T> valueType) {
        return DataSerializer.deserialize(map, valueType);
    }

    public String toJson(Object value) {
        return DataSerializer.serialize(value);
    }

    public void pipeline(RedisCallback<?> callback) {
        stringRedisTemplate.executePipelined(callback);
    }
    private <T> List<T> mappingToElement(Collection<String> jsonList, Class<T> valueType){
        return jsonList.stream().map(json -> toObject(json, valueType)).collect(Collectors.toList());
    }

    private boolean isEmpty(String key) {
        return stringRedisTemplate.hasKey(key) == null;
    }

    private void expire(Long result, String key, Duration timeout) {
        if (timeout != null && result != null && result == 1) {
            stringRedisTemplate.expire(key, timeout);
        }
    }

    private void expire(String key, Duration timeout) {
        if (timeout != null) {
            stringRedisTemplate.expire(key, timeout);
        }
    }
}