package com.jongho.common.cache;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class CustomCachePageable {
    @Builder.Default
    private int offset = 0;
    @Builder.Default
    private int limit = -1;
}
