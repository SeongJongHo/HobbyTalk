package com.jongho.common.cache;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomCacheAdvice {

    private final List<ICustomCacheManager> customCacheManager;

    @Around("@annotation(com.jongho.common.cache.CustomCacheable)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomCacheable cacheable = findAnnotation(joinPoint);

        ICustomCacheManager manager = findCacheManager(CustomCacheType.from(cacheable.type()));

        return manager.process(
            cacheable,
            extractKeyArgs(joinPoint, cacheable.keys()),
            findReturnType(joinPoint),
            getCustomCachePageable(joinPoint),
            joinPoint::proceed
        );
    }

    private CustomCachePageable getCustomCachePageable(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        CustomCachePageable.CustomCachePageableBuilder customCachePageable = CustomCachePageable.builder();
        IntStream.range(0, parameterNames.length)
                .forEach(i -> {
                    if(parameterNames[i].equals("offset")) {
                        customCachePageable.offset((Integer) args[i]);
                    } else if(parameterNames[i].equals("limit")) {
                        customCachePageable.limit((Integer) args[i]);
                    }
                });
        return customCachePageable.build();
    }

    private String[] extractKeyArgs(ProceedingJoinPoint joinPoint, String[] keyNames) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        return Arrays.stream(keyNames)
            .map(keyName -> IntStream.range(0, parameterNames.length)
                .filter(i -> parameterNames[i].equals(keyName))
                .mapToObj(i -> String.valueOf(args[i]))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                    "메서드에 존재하지 않는 파라미터명입니다.: " + keyName))
            )
            .toArray(String[]::new);
    }

    private CustomCacheable findAnnotation(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod().getAnnotation(CustomCacheable.class);

    }

    private Type findReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        return method.getGenericReturnType();
    }

    private ICustomCacheManager findCacheManager(CustomCacheType cacheType) {
        return customCacheManager.stream()
            .filter(manager -> manager.support(cacheType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "지원하지 않는 반환 타입입니다.: " + cacheType));
    }
}
