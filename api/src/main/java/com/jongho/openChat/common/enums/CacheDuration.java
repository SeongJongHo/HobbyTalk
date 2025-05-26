package com.jongho.openChat.common.enums;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheDuration {
    BATCH(Duration.ofMinutes(20));

    private final Duration duration;
}
