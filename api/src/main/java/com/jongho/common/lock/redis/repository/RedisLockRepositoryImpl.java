package com.jongho.common.lock.redis.repository;

import com.jongho.common.lock.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisLockRepositoryImpl implements LockRepository {
    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean acquireLock(String key) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "gift", Duration.ofMillis(2000)));
    }

    @Override
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
