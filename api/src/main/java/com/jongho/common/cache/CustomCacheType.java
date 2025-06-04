package com.jongho.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomCacheType {
    OBJECT(Type.OBJECT),
    LIST(Type.LIST);

    private final String name;

    public static CustomCacheType from(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
    public static class Type {
        public static final String OBJECT = "OBJECT";
        public static final String LIST = "LIST";
    }

}
