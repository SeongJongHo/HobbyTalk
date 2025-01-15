package com.jongho.common.lock.repository;

import com.jongho.common.lock.LockKey;

public interface LockRepository {
    boolean acquireLock(String key);
    void releaseLock(String key);
}
