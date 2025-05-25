package com.jongho.openChat.infra.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.application.repository.IOpenChatRedisRepository;
import com.jongho.openChat.domain.model.OpenChat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenChatRedisRepository implements IOpenChatRedisRepository {
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
    public void insertOpenChat(OpenChat openChat){
        baseRedisTemplate.rPushList(
                RedisKeyGeneration.getChatRoomMessageKey(openChat.getOpenChatRoomId()),
                openChat);
    };

    @Override
    public void updateLastOpenChat(OpenChat openChat){
        baseRedisTemplate.set(
                RedisKeyGeneration.getLastMessageKey(openChat.getOpenChatRoomId()),
                openChat);
    };
}
