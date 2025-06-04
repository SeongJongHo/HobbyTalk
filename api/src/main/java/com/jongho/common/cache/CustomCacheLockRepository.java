package com.jongho.common.cache;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCacheLockRepository {

    private static final String PREFIX = "cache-lock:";
    private static final Duration TTL = Duration.ofSeconds(3);
    private final StringRedisTemplate redisTemplate;

    public boolean lock(String key) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(
            generateLockKey(key),
            "gift",
            TTL
        ));
    }

    public void unlock(String key) {
        redisTemplate.delete(generateLockKey(key));
    }

    private String generateLockKey(String key) {
        return PREFIX + key;
    }
}
