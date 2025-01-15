package com.jongho.common.lock.redis.service;

import com.jongho.common.lock.LockKey;
import com.jongho.common.lock.repository.LockRepository;
import com.jongho.common.lock.service.LockService;

public class RedisLockServiceImpl implements LockService {

    private final LockRepository lockRepository;

    public RedisLockServiceImpl(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    @Override
    public boolean acquireLock(Long id, LockKey key) {
        return lockRepository.acquireLock(genKey(id, key));
    }

    @Override
    public void releaseLock(Long id, LockKey key) {
        lockRepository.releaseLock(genKey(id, key));
    }

    private String genKey(Long id, LockKey key) {
        return key.getKey() + id;
    }
}