package com.jongho.openChat.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheSize {
    DEFAULT(Size.DEFAULT),
    CHAT(Size.CHAT),
    CHAT_ROOM(Size.CHAT_ROOM),
    CHAT_BATCH(Size.CHAT_BATCH);

    private final int size;

    public static CacheSize from(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static CacheSize fromSize(int size) {
        for (CacheSize cacheSize : values()) {
            if (cacheSize.size == size) {
                return cacheSize;
            }
        }
        return DEFAULT;
    }

    public static class Size {
        public static final int DEFAULT = 100;
        public static final int CHAT_ROOM = 1000;
        public static final int CHAT = 1000;
        public static final int CHAT_BATCH = 2000;
    }
}
