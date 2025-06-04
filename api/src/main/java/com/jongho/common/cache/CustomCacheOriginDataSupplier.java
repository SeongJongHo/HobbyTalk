package com.jongho.common.cache;

@FunctionalInterface
public interface CustomCacheOriginDataSupplier<T> {

    T get() throws Throwable;
}
