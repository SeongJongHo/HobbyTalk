package com.jongho.openChat.dao.repository;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.OpenChatRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OpenChatRedisRepositoryImpl implements OpenChatRedisRepository {
    private final BaseRedisTemplate baseRedisTemplate;

    @Override
    public Optional<OpenChat> selectLastOpenChatByChatRoomId(Long openChatRoomId){
        return Optional.ofNullable(baseRedisTemplate.getData(
                RedisKeyGeneration.getLastMessageKey(openChatRoomId),
                OpenChat.class));
    }
    @Override
    public List<OpenChat> selectOpenChatListByChatRoomId(Long openChatRoomId){
        return baseRedisTemplate.getReverseRangeAllListData(
                RedisKeyGeneration.getChatRoomMessageKey(openChatRoomId),
                OpenChat.class);
    };

    @Override
    public List<OpenChat> selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit){
        return baseRedisTemplate.getReverseRangeListData(
                RedisKeyGeneration.getChatRoomMessageKey(openChatRoomId),
                OpenChat.class,
                offset,
                limit);
    };
    @Override
    public void insertOpenChat(OpenChat openChat){
        baseRedisTemplate.setListData(
                RedisKeyGeneration.getChatRoomMessageKey(openChat.getOpenChatRoomId()),
                openChat);
    };
    @Override
    public void updateLastOpenChat(OpenChat openChat){
        baseRedisTemplate.setData(
                RedisKeyGeneration.getLastMessageKey(openChat.getOpenChatRoomId()),
                openChat);
    };
}
