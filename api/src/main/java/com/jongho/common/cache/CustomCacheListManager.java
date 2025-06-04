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
    private static final CustomCacheType CACHE_TYPE = CustomCacheType.LIST;

    public Object process(
        CustomCacheable cacheable,
        String[] keys, Type returnType,
        CustomCachePageable pageable,
        CustomCacheOriginDataSupplier<?> originDataSupplier
    ) throws Throwable {
        String key = generateKey(cacheable.prefix(), keys);
        List<?> cachedData = getCachedData(key, returnType, pageable.getOffset(), pageable.getLimit());

        if (cachedData != null && !cachedData.isEmpty()) {
            return cachedData;
        }

        if (!customCacheLockProvider.lock(key)) {
            return originDataSupplier.get();
        }

        try {
            return refresh(originDataSupplier, key);
        } finally {
            customCacheLockProvider.unlock(key);
        }
    }

    @Override
    public boolean support(CustomCacheType cacheType) {
        return CACHE_TYPE.equals(cacheType);
    }


    private Object refresh(CustomCacheOriginDataSupplier<?> originDataSupplier, String key)
        throws Throwable {
        List<?> result = (List<?>) originDataSupplier.get();

        redisTemplate.lPushList(key, result);
        redisTemplate.trimList(key, 0, CacheSize.CHAT_ROOM.getSize() - 1);

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
}
