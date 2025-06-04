package com.jongho.common.cache;

import java.lang.reflect.Type;

public interface ICustomCacheManager {

    Object process(CustomCacheable cacheable, String[] keys, Type returnType,
        CustomCachePageable pageable,
        CustomCacheOriginDataSupplier<?> originDataSupplier) throws Throwable;

    boolean support(CustomCacheType returnType);
}
