package com.jongho.common.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.serializer.DataSerializer;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomCacheManager implements ICustomCacheManager {

    private static final String DELIMITER = ":";
    private static final CustomCacheType CACHE_TYPE = CustomCacheType.OBJECT;
    private final BaseRedisTemplate redisTemplate;
    private final CustomCacheLockRepository customCacheLockProvider;

    @Override
    public Object process(
        CustomCacheable cacheable,
        String[] keys, Type returnType,
        CustomCachePageable pageable,
        CustomCacheOriginDataSupplier<?> originDataSupplier
    ) throws Throwable {
        String key = generateKey(cacheable.prefix(), keys);
        CustomCache cachedData = redisTemplate.get(key, CustomCache.class);

        if (cachedData == null) {
            return refresh(originDataSupplier, key, cacheable.ttlSeconds());
        }

        cachedData = DataSerializer.deserialize(cachedData, CustomCache.class);

        if (!cachedData.isExpired()) {
            return cachedData.parseData(returnType);
        }

        if (!customCacheLockProvider.lock(key)) {
            return cachedData.parseData(returnType);
        }

        try {
            return refresh(originDataSupplier, key, cacheable.ttlSeconds());
        } finally {
            customCacheLockProvider.unlock(key);
        }
    }

    private Object refresh(CustomCacheOriginDataSupplier<?> originDataSupplier, String key,
        long ttl) throws Throwable {
        Object result = originDataSupplier.get();
        CustomCacheTTL ttlObject = CustomCacheTTL.of(ttl);
        CustomCache cache = CustomCache.of(result, ttlObject.getLogicalTTL());

        redisTemplate.set(key, cache, ttlObject.getPhysicalTTL());

        return result;
    }

    private String generateKey(String prefix, Object[] args) {
        StringBuilder keyBuilder = new StringBuilder(prefix);
        for (Object arg : args) {
            keyBuilder.append(DELIMITER).append(arg);
        }
        return keyBuilder.toString();
    }

    @Override
    public boolean support(CustomCacheType cacheType) {
        return CACHE_TYPE.equals(cacheType);
    }
}
