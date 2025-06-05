package com.jongho.common.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.openChat.common.enums.CacheSize;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomCacheListManager implements ICustomCacheManager {

    private final BaseRedisTemplate redisTemplate;
    private final CustomCacheLockRepository customCacheLockProvider;
    private static final String DELIMITER = ":";
    private static final String METADATA_KEY = "metadata";
    private static final CustomCacheType CACHE_TYPE = CustomCacheType.LIST;

    public Object process(
        CustomCacheable cacheable,
        String[] keys, Type returnType,
        CustomCachePageable pageable,
        CustomCacheOriginDataSupplier<?> originDataSupplier
    ) throws Throwable {
        String key = generateKey(cacheable.prefix(), keys);
        CacheSize cacheSize = CacheSize.fromSize(cacheable.cacheSize());

        if (pageable.getOffset() >= cacheSize.getSize()) {
            return originDataSupplier.get();
        }

        List<?> cachedData = getCachedData(key, returnType, pageable.getOffset(), pageable.getLimit());
        CustomCache customCache = getCustomCache(key);

        if (cachedData == null || cachedData.isEmpty()) {
            return refresh(originDataSupplier, key, cacheable.ttlSeconds(), cacheSize.getSize());
        }

        if (customCache != null && !customCache.isExpired()) {
            return cachedData;
        }

        if (!customCacheLockProvider.lock(key)) {
            return cachedData;
        }

        try {
            return refresh(originDataSupplier, key, cacheable.ttlSeconds(), cacheSize.getSize());
        } finally {
            customCacheLockProvider.unlock(key);
        }
    }

    @Override
    public boolean support(CustomCacheType cacheType) {
        return CACHE_TYPE.equals(cacheType);
    }


    private Object refresh(
        CustomCacheOriginDataSupplier<?> originDataSupplier,
        String key,
        long ttl,
        int cacheSizeLimit
    ) throws Throwable {
        String data = "present";
        List<?> result = (List<?>) originDataSupplier.get();
        CustomCacheTTL ttlObject = CustomCacheTTL.of(ttl);
        CustomCache cache = CustomCache.of(data, ttlObject.getLogicalTTL());

        redisTemplate.lPushList(key, result);
        redisTemplate.trimList(key, 0, cacheSizeLimit - 1);
        redisTemplate.set(metadataKey(key), cache, ttlObject.getPhysicalTTL());
        return result;
    }

    private String generateKey(String prefix, Object[] args) {
        StringBuilder keyBuilder = new StringBuilder(prefix);
        for (Object arg : args) {
            keyBuilder.append(DELIMITER).append(arg);
        }
        return keyBuilder.toString();
    }

    private List<?> getCachedData(String key, Type returnType, int offset, int limit) {
        if (returnType instanceof ParameterizedType parameterizedType) {
            Type arg = parameterizedType.getActualTypeArguments()[0];

            if (arg instanceof Class<?> elementType) {
                return redisTemplate.getList(key, elementType, offset, limit);
            }
        }

        return null;
    }

    private CustomCache getCustomCache(String key) {
        return redisTemplate.get(metadataKey(key), CustomCache.class);
    }

    private String metadataKey(String key) {
        return key + DELIMITER + METADATA_KEY;
    }
}
