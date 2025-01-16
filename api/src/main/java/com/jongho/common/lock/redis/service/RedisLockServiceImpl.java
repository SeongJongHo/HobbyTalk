package com.jongho.common.lock.redis.service;

import com.jongho.common.lock.LockKey;
import com.jongho.common.lock.repository.LockRepository;
import com.jongho.common.lock.service.LockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockServiceImpl implements LockService {

    private final LockRepository lockRepository;

    @Override
    public boolean acquireLock(Long id, LockKey key) {
        return lockRepository.acquireLock(genKey(id, key));
    }

    @Override
    public boolean acquireLock(LockKey key) {
        return lockRepository.acquireLock(key.getKey());
    }

    @Override
    public void releaseLock(LockKey key) {
        lockRepository.releaseLock(key.getKey());
    }

    @Override
    public void releaseLock(Long id, LockKey key) {
        lockRepository.releaseLock(genKey(id, key));
    }

    private String genKey(Long id, LockKey key) {
        return key.getKey() + id;
    }
}