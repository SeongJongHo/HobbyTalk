package com.jongho.common.advice;

import com.jongho.common.exception.RedisMultiExecException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedisMultiExecAdvice {

    private final StringRedisTemplate redisTemplate;

    @Around("@annotation(com.jongho.common.annotaition.RedisMultiExec)")
    public Object aroundMultiExec(ProceedingJoinPoint pjp) {
        redisTemplate.multi();
        try {
            Object result = pjp.proceed();

            redisTemplate.exec();

            return result;
        } catch (Throwable ex) {
            log.error("Redis transaction failed, discarding changes", ex);
            redisTemplate.discard();
            throw new RedisMultiExecException(
                "Redis transaction failed, discarding changes: " + ex.getMessage());
        }
    }
}
