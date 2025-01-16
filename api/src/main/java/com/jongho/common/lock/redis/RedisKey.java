package com.jongho.common.lock.redis;

import com.jongho.common.lock.LockKey;

public enum RedisKey implements LockKey {
    BATCH("batch");
    private final String key;

    RedisKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
