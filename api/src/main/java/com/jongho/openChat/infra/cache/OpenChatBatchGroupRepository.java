package com.jongho.openChat.infra.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.application.repository.IOpenChatBatchGroupRepository;
import com.jongho.openChat.common.enums.CacheDuration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenChatBatchGroupRepository implements IOpenChatBatchGroupRepository {
    private final BaseRedisTemplate baseRedisTemplate;

    @Override
    public void save(Long chatId) {
        baseRedisTemplate.lPushList(
            RedisKeyGeneration.getChatGroupKey(),
            chatId,
            CacheDuration.BATCH.getDuration()
        );
    }

    @Override
    public void rename() {
        baseRedisTemplate.rename(
            RedisKeyGeneration.getChatGroupKey(),
            RedisKeyGeneration.getChatGroupProcessingKey()
        );
    }

    public List<Long> lPopProcessing(int count) {
        return baseRedisTemplate.lPopList(
            RedisKeyGeneration.getChatGroupProcessingKey(),
            Long.class,
            count
        );
    }
}
