package com.jongho.common.cache;

import java.time.Duration;
import lombok.Getter;

@Getter
public class CustomCacheTTL {

    public static final long PHYSICAL_TTL_DELAY_SECONDS = 10;
    private Duration logicalTTL;
    private Duration physicalTTL;

    public static CustomCacheTTL of(long ttlSeconds) {
        CustomCacheTTL customCacheTTL = new CustomCacheTTL();
        customCacheTTL.logicalTTL = Duration.ofSeconds(ttlSeconds);
        customCacheTTL.physicalTTL = customCacheTTL.logicalTTL.plusSeconds(
            PHYSICAL_TTL_DELAY_SECONDS);
        return customCacheTTL;
    }
}
