package com.jongho.common.lock.redis.repository;

import com.jongho.common.lock.repository.LockRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisLockRepositoryImpl implements LockRepository {
    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean acquireLock(String key) {
        return Boolean.TRUE.equals(
            redisTemplate.opsForValue().setIfAbsent(key, "gift", Duration.ofSeconds(5)));
    }

    @Override
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
