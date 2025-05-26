package com.jongho.openChat.infra.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.application.repository.IOpenChatBatchRepository;
import com.jongho.openChat.common.enums.CacheDuration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenChatBatchRepository implements IOpenChatBatchRepository {
    private final BaseRedisTemplate baseRedisTemplate;

    public void save(Long chatRoomId, Long chatId) {
        baseRedisTemplate.lPushList(
            RedisKeyGeneration.getChatBatchKey(chatRoomId),
            chatId,
            CacheDuration.BATCH.getDuration()
        );
    }

    public void rename(Long chatRoomId) {
        baseRedisTemplate.rename(
            RedisKeyGeneration.getChatBatchKey(chatRoomId),
            RedisKeyGeneration.getChatBatchProcessingKey(chatRoomId)
        );
    }
}
