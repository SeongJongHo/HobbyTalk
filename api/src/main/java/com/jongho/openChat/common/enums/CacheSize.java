package com.jongho.openChat.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheSize {
    CHAT(1000),
    CHAT_ROOM(1000),
    CHAT_BATCH(2000);

    private final int size;
}
