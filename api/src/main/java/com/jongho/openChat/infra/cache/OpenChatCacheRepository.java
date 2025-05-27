package com.jongho.openChat.infra.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.application.repository.IOpenChatRedisRepository;
import com.jongho.openChat.common.enums.CacheDuration;
import com.jongho.openChat.common.enums.CacheSize;
import com.jongho.openChat.domain.model.OpenChat;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenChatCacheRepository implements IOpenChatRedisRepository {
    private final BaseRedisTemplate baseRedisTemplate;

    @Override
    public Optional<OpenChat> selectLastOpenChatByChatRoomId(Long openChatRoomId){
        return Optional.ofNullable(baseRedisTemplate.get(
                RedisKeyGeneration.getLastMessageKey(openChatRoomId),
                OpenChat.class));
    }
    @Override
    public List<OpenChat> selectOpenChatListByChatRoomId(Long openChatRoomId){
        return baseRedisTemplate.getReverseList(
            RedisKeyGeneration.getChatRoomMessageKey(openChatRoomId),
            OpenChat.class,
            0,
            -1);
    };

    @Override
    public List<OpenChat> selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit){
        return baseRedisTemplate.getReverseList(
                RedisKeyGeneration.getChatRoomMessageKey(openChatRoomId),
                OpenChat.class,
                offset,
                limit);
    };
    @Override
    public void save(OpenChat openChat) {
        baseRedisTemplate.set(
            RedisKeyGeneration.getChatMessageKey(openChat.getSnowflakeId()),
            openChat, CacheDuration.BATCH.getDuration());
        saveCache(openChat.getOpenChatRoomId(),
            openChat.getSnowflakeId(), CacheDuration.BATCH.getDuration());
    }

    private void saveCache(Long openChatRoomId, Long snowflakeId, Duration timeout) {
        baseRedisTemplate.lPushList(
            RedisKeyGeneration.getChatRoomMessageKey(openChatRoomId),
            snowflakeId,
            CacheDuration.BATCH.getDuration());
    }

    @Override
    public void updateLastOpenChat(OpenChat openChat){
        baseRedisTemplate.set(
                RedisKeyGeneration.getLastMessageKey(openChat.getOpenChatRoomId()),
                openChat);
    }

    @Override
    public Long getSize(Long chatRoomId) {
        return baseRedisTemplate.getListSize(
            RedisKeyGeneration.getChatRoomMessageKey(chatRoomId));
    }

    @Override
    public Long getBatchSize(Long chatRoomId) {
        return baseRedisTemplate.getListSize(
            RedisKeyGeneration.getChatBatchKey(chatRoomId));
    }

    @Override
    public void trimCacheToSize(Long chatRoomId) {
        baseRedisTemplate.trimList(
            RedisKeyGeneration.getChatRoomMessageKey(chatRoomId),
            0,
            CacheSize.CHAT.getSize()-1);
    }
}
