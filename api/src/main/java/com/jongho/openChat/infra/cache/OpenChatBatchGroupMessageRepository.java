package com.jongho.openChat.infra.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.application.repository.IOpenChatBatchGroupMessageRepository;
import com.jongho.openChat.common.enums.CacheDuration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenChatBatchGroupMessageRepository implements IOpenChatBatchGroupMessageRepository {
    private final BaseRedisTemplate baseRedisTemplate;

    @Override
    public void save(Long chatRoomId) {
        baseRedisTemplate.lPushList(
            RedisKeyGeneration.getChatGroupMessageKey(),
            chatRoomId,
            CacheDuration.BATCH.getDuration()
        );
    }

    @Override
    public void rename() {
        baseRedisTemplate.rename(
            RedisKeyGeneration.getChatGroupMessageKey(),
            RedisKeyGeneration.getChatGroupMessageKey()
        );
    }

    @Override
    public List<Long> rPopProcessing(int count) {
        return baseRedisTemplate.rPopList(
            RedisKeyGeneration.getChatGroupMessageKey(),
            Long.class,
            count
        );
    }
}
