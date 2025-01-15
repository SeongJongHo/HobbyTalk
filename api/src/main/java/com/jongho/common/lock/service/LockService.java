package com.jongho.common.lock.service;

import com.jongho.common.lock.LockKey;

public interface LockService {
    boolean acquireLock(Long id, LockKey key);
    void releaseLock(Long id, LockKey key);
}

